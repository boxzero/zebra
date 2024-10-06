package com.houseclay.zebra.service.impl;

//import com.amazonaws.services.mediastoredata.model.PutObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.houseclay.zebra.CloudOperations.CloudOperations;
import com.houseclay.zebra.dto.*;
import com.houseclay.zebra.dto.AddPropertyDTOs.*;
import com.houseclay.zebra.exceptionHandling.IdNotFoundException;
import com.houseclay.zebra.model.Configure.Location;
import com.houseclay.zebra.model.common.Address;
import com.houseclay.zebra.model.common.Amenities;
import com.houseclay.zebra.model.common.BaseTimeStamp;
import com.houseclay.zebra.model.common.PropertySpecs;
import com.houseclay.zebra.model.rental.Images;
import com.houseclay.zebra.model.rental.Owner;
import com.houseclay.zebra.model.rental.PropertyRent;
import com.houseclay.zebra.repository.ImageRepository;
import com.houseclay.zebra.repository.LocationRepository;
import com.houseclay.zebra.repository.PropertyForRentRepository;
import com.houseclay.zebra.service.PropertRentService;
//import jdk.internal.org.jline.utils.Log;
//import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class PropertyRentServiceImpl implements PropertRentService {


    @Autowired
    PropertyForRentRepository propertyForRentRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    private AmazonS3 s3Client;
    @Value("${application.bucket.name}")
    private String bucketName;
    @Autowired
    private LocationRepository locationRepository;





    @Override
    public PropertyRent saveResidentialPropertyForRent(PropertyRent property_for_rent) {
        return propertyForRentRepository.save(property_for_rent);
    }



    @Override
    public SaveAndUrlResponseDTO saveResidentialPropertyForRentv2(PropertyRent property_for_rent)  {
        List<Images> images=property_for_rent.getImageMap();
       List<String>urls= uploadImages(images);
        return new SaveAndUrlResponseDTO(urls, propertyForRentRepository.save(property_for_rent));
    }



    @Override
    public PropertyRentDTO findPropertyForRentById(UUID uuid) {

        //String jsonData = null;
        //Fetch the List of Images from the Images Table by providing the property uuid

        Optional<PropertyRent> propertyRent = propertyForRentRepository.findById(uuid);
        PropertyRentDTO propertyRentDTO;
        try{
            List<ImageResponse> list_of_images = propertyRent.get().
                    getImageMap().stream().map(this::mapToImageResponse).collect(Collectors.toList());
            propertyRentDTO = mapToPropertyRentDTO(propertyRent.get(),list_of_images);
        }
        catch (Exception e){
            throw new NullPointerException("The Property is Not Present");
        }


        return propertyRentDTO;
    }



    // delete property from Database and CLOUD
    @Override
    public Boolean deletePropertyForRentById(UUID uuid) {

        Optional<PropertyRent> propertyForRent = propertyForRentRepository.findById(uuid);

        CloudOperations cloudOperations=new CloudOperations(s3Client, bucketName);
        if(propertyForRent.isPresent()){
            cloudOperations.deleteFolder(propertyForRent.get().getFolderName());
            propertyForRentRepository.deleteById(uuid);
            return true;
        }

        return false;

    }

    @Override
    public PropertyRent updatePropertyForRentById(UUID uuid) {
        return null;
    }



    private PropertyRentDTO mapToPropertyRentDTO(PropertyRent proRent, List<ImageResponse> imageResponse){

        PropertyRentDTO propertyRentDTO = PropertyRentDTO.builder().
                name(proRent.getName()).title(proRent.getTitle()).owner(proRent.getOwner()).
                isManaged(proRent.isManaged()).propertySpecs(proRent.getPropertySpecs()).
                imageMap(imageResponse).active_status(proRent.isActive_status()).
                inactive_reason(proRent.getInactive_reason()).posted_on(proRent.getPosted_on()).
                available_from(proRent.getAvailable_from()).
                property_rent(proRent.getProperty_rent())
                .property_maintenance(proRent.getProperty_maintenance()).
//                preferred_tenant_type(proRent.getPreferred_tenant_type()).created_by(proRent.getBaseTimeStamp().getCreated_by())
//                        .created_on(proRent.getBaseTimeStamp().getCreated_on()).
                build();

         return propertyRentDTO;
    }

    private ImageResponse mapToImageResponse(Images image){

        String downloadUrl= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/").path(image.getImage_id().toString()).toUriString();

        ImageResponse imageResponse = ImageResponse.builder().id(image.getImage_id())
                .name(image.getName()).contentType(image.getContentType())
                .size(image.getSize()).url(downloadUrl).build();
        return imageResponse;
    }


    /**
     * This method is for converting the json String to PropertyRent object using ObjectMapper
     * It is also binding the data of the Image List of Files into the PropertyRent Object
     * @param jsonProperty
     * @param files
     * @return
     */
    public PropertyRent getJson(String jsonProperty, List<MultipartFile> files){
        log.info("Inside getJson Method---- PropertyRentServiceImpl");
        PropertyRent propertyRentJson = new PropertyRent();

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            propertyRentJson = objectMapper.readValue(jsonProperty,PropertyRent.class);
            System.out.println(propertyRentJson);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }




        List<Images> list_of_Images = new ArrayList<Images>();
        for(MultipartFile f: files){
            Images image = null;
            try {
                image = Images.builder().
                        name(StringUtils.cleanPath(f.getOriginalFilename())).
                        contentType(f.getContentType()).baseTimeStamp(BaseTimeStamp.builder().created_by("SYSTEM").created_on(new Date()).build())
                        .size(f.getSize()).image_data(f.getBytes()).build();
            } catch (IOException e) {
                e.printStackTrace();
            }
            list_of_Images.add(image);
        }

        propertyRentJson.setImageMap(list_of_Images);
        return propertyRentJson;
    }




//-------------------------------------------------------






    // uploading images to s3 storage
    private List<String> uploadImages(List<Images>images){
        List<String>urls=new ArrayList<>();
        String folderName="propertyImages";
        for(Images image:images){
            String fileName=System.currentTimeMillis()+ "_" + UUID.randomUUID()+"_"+image.getName();
            File file=new File(fileName);
            try(OutputStream os=new FileOutputStream(file)){
                os.write(image.getImage_data());
            }
            catch(Exception e){
                e.printStackTrace();
            }
            s3Client.putObject(bucketName+"/"+folderName, fileName, file);
            urls.add(s3Client.getUrl(bucketName+"/"+folderName, fileName).toExternalForm());

            image.setImageNameInCloud(fileName);

            file.delete();
        }
        return urls;

    }






    @Override
    public List<ViewAllPropertiesDTO> viewAllDetails(){
        List<ViewAllPropertiesDTO> allProperties=new ArrayList<>();
        List<PropertyRent>l= propertyForRentRepository.findAll();
        for(PropertyRent p: l){
            ViewAllPropertiesDTO viewAllPropertiesDTO= ViewAllPropertiesDTO
                    .builder()
                    .property_id(p.getProperty_id())
                    .name(p.getName())
                    .bhkType(p.getPropertySpecs().getBeds()+" BHK")
                    .locality(p.getPropertySpecs().getFull_address().getLocality())
                    .rent(p.getProperty_rent())
                    .availableFrom(p.getAvailable_from())
                    .furniture(p.getPropertySpecs().getFurnishing())
                    .build();
            allProperties.add(viewAllPropertiesDTO);
        }
        return allProperties;
    }





    @Override
    public ViewSpecificPropertyDTO viewSpecificProperty(UUID propertyId) throws IdNotFoundException {
        Optional<PropertyRent> propertyRentOptional = propertyForRentRepository.findById(propertyId);
        if (!propertyRentOptional.isPresent()) {
            throw new IdNotFoundException(propertyId, "PropetyRent Id not found !!");
        }
        PropertyRent propertyRent = propertyRentOptional.get();
        ViewSpecificPropertyDTO viewSpecificPropertyDTO;
        try {
             viewSpecificPropertyDTO = ViewSpecificPropertyDTO
                    .builder()
                    .property_id(propertyRent.getProperty_id())
                    .name(propertyRent.getName())
                    .title(propertyRent.getTitle())
                    .isManaged(propertyRent.isManaged())
                    .propertySpecs(propertyRent.getPropertySpecs())
                    .active_status(propertyRent.isActive_status())
                    .inactive_reason(propertyRent.getInactive_reason())
                    .posted_on(propertyRent.getPosted_on())
                    .available_from(propertyRent.getAvailable_from())
                    .property_rent(propertyRent.getProperty_rent())
                    .property_maintenance(propertyRent.getProperty_maintenance())
                    .preferred_tenant_type(propertyRent.getPreferred_tenant_type())
                    .baseTimeStamp(propertyRent.getBaseTimeStamp())
                    .propertyFor(propertyRent.getPropertyFor())
                    .expected_Deposit(propertyRent.getExpected_Deposit())
                    .rent_negotiable(propertyRent.isRent_negotiable())
                    .who_will_show_the_property(propertyRent.getWho_will_show_the_property())
                    .showProperty_contact(propertyRent.getShowProperty_contact())
                    .availability(propertyRent.getAvailability())
                    .startTime(propertyRent.getStartTime())
                    .endTime(propertyRent.getEndTime())
                    .build();
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to make DTO");

        }
        return viewSpecificPropertyDTO;
    }







    public List<Images> convertMulipartToListOfImages(List<MultipartFile> images, String loggedInUser){
        List<Images> list_of_Images = new ArrayList<Images>();
        for (MultipartFile f : images) {
            Images image = null;
            try {
                image = Images.builder().
                        name(StringUtils.cleanPath(f.getOriginalFilename())).
                        contentType(f.getContentType()).baseTimeStamp(BaseTimeStamp.builder().created_by(loggedInUser).created_on(new Date()).build())
                        .size(f.getSize()).image_data(f.getBytes()).build();
            } catch (IOException e) {
                e.printStackTrace();
            }
            list_of_Images.add(image);
        }
return list_of_Images;
    }












    @Override
    public PropertyRent updatePropertyMultipartData(UUID propertyId, PropertyRent newProperty, List<MultipartFile> images, String loggedInUser) throws IdNotFoundException {
        Optional<PropertyRent>propertyRent=propertyForRentRepository.findById(propertyId);
        if(!propertyRent.isPresent()){
            throw new IdNotFoundException(propertyId, "propertyRent Id Not Found");
        }

        PropertyRent existingProperty=propertyRent.get();


        List<Images>newImages=convertMulipartToListOfImages(images, loggedInUser);
        List<Images>oldImages=existingProperty.getImageMap();



        CloudOperations cloudOperations=new CloudOperations(s3Client, bucketName);
        cloudOperations.deleteAllImages(existingProperty.getFolderName(), oldImages);
        cloudOperations.uploadImagesToFolder(newImages, existingProperty.getFolderName());


        existingProperty.getBaseTimeStamp().setChanged_on(new Date());
        existingProperty.getBaseTimeStamp().setChanged_by(loggedInUser);

        existingProperty.setName(newProperty.getName());
        existingProperty.setTitle(newProperty.getTitle());
        existingProperty.setOwner(newProperty.getOwner());
        existingProperty.setManaged(newProperty.isManaged());
        existingProperty.setPropertySpecs(newProperty.getPropertySpecs());
        existingProperty.setPosted_on(newProperty.getPosted_on());
        existingProperty.setAvailable_from(newProperty.getAvailable_from());
        existingProperty.setProperty_rent(newProperty.getProperty_rent());
        existingProperty.setProperty_maintenance(newProperty.getProperty_maintenance());
        existingProperty.setPreferred_tenant_type(newProperty.getPreferred_tenant_type());

        existingProperty.setActive_status(false); // ------------------
//  need to handle null values ([propertyRent] : active_status, inactive reason, isManaged, [propertySpecs] : flooring, latitude, longitude, locationUrl)

        return propertyForRentRepository.save(existingProperty);
    }








    private PropertyRent convertJsonToProperty(String jsonProperty) {
        AddPropertyDTO addPropertyDTO = new AddPropertyDTO();
        PropertyRent propertyRent = new PropertyRent();
        try{

            ObjectMapper objectMapper=new ObjectMapper();
            addPropertyDTO = objectMapper.readValue(jsonProperty, AddPropertyDTO.class);


            AmenitiesDTO amenitiesDTO = addPropertyDTO.getAmenities();
            LocalityDetailsDTO localityDetailsDTO = addPropertyDTO.getLocalityDetails();
            OwnerDetailsDTO ownerDetailsDTO = addPropertyDTO.getOwnerDetails();
            PropertyDetailsDTO propertyDetailsDTO = addPropertyDTO.getPropertyDetails();
            RentalDetailsDTO rentalDetailsDTO = addPropertyDTO.getRentalDetails();
            ScheduleDTO scheduleDTO = addPropertyDTO.getSchedule();
            Map<String ,Boolean> amenitiesMap = amenitiesDTO.getAmenities();


            Owner owner = Owner.builder()
                    .owner_name(ownerDetailsDTO.getFirstName()+" "+ownerDetailsDTO.getLastName())
                    .owner_email(ownerDetailsDTO.getEmail())
                    .owner_contact(ownerDetailsDTO.getContactNumber())
                    .notes(ownerDetailsDTO.getNotes())
                    .build();

            Optional<Location> location = locationRepository.findByCity(localityDetailsDTO.getCity());

            Address address = Address.builder()
                    .city(localityDetailsDTO.getCity())
                    .street_name(localityDetailsDTO.getLandmark()).
                    locality(localityDetailsDTO.getLocality())
                    .property_name(propertyDetailsDTO.getName())
                    .house_no(localityDetailsDTO.getHouseNumber())
                    .state(location.get().getState())
                    .pincode(location.get().getPinCode())
                    .build();

            Amenities amenitiesObject = Amenities.builder()
                    .lift(amenitiesMap.get("lift"))
                    .club_house(amenitiesMap.get("clubHouse"))
                    .intercom(amenitiesMap.get("intercom"))
                    .swimming_pool(amenitiesMap.get("swimmingPool"))
                    .children_play_area(amenitiesMap.get("childrenPlayArea"))
                    .fire_safety(amenitiesMap.get("fireSafety"))
                    .security(amenitiesMap.get("security"))
                    .park(amenitiesMap.get("park"))
                    .rain_water_harvesting(amenitiesMap.get("rainwaterHarvesting"))
                    .sewage_treatment_plant(amenitiesMap.get("sewageTreatmentPlant"))
                    .housekeeping(amenitiesMap.get("housekeeping"))
                    .power_backup(amenitiesMap.get("powerBackup"))
                    .internet_services(amenitiesMap.get("internetServices"))
                    .servant_room(amenitiesMap.get("servantRoom"))
                    .gas_pilpeline(amenitiesMap.get("gasPipeline"))
                    .shopping_centre(amenitiesMap.get("shoppingCentre"))
                    .gym((amenitiesDTO.getGym().equals("yes")))
                    .security(amenitiesDTO.getGatedSecurity().equals("yes"))
                    .nonVegAllowed(amenitiesDTO.getNonVegAllowed().equals("yes"))
                    .build();

            PropertySpecs propertySpecs = PropertySpecs.builder()
                    .full_address(address)
                    .type(propertyDetailsDTO.getType())
                    .build_up_area(propertyDetailsDTO.getBuiltupArea())
                    .bath(amenitiesDTO.getBathroom())
                    .balcony(amenitiesDTO.getBathroom())
                    .parking(rentalDetailsDTO.getParking())
                    .facing(propertyDetailsDTO.getFacing())
                    .property_age(propertyDetailsDTO.getPropertyAge())
                    .furnishing(rentalDetailsDTO.getFurniture())
                    .city(localityDetailsDTO.getCity())
                    .description(rentalDetailsDTO.getDescription())
                    .amenitiesMap(amenitiesObject)
                    .beds(propertyDetailsDTO.getBhkType().substring(0, 2))
                    .totalFloors(propertyDetailsDTO.getTotalFloor())
                    .floor(propertyDetailsDTO.getFloor())
                    .build();


            propertyRent = PropertyRent.builder()
                    .name(propertyDetailsDTO.getName())
                    .title(propertyDetailsDTO.getBhkType()+" in "+propertyDetailsDTO.getName())
                    .owner(owner)
                    .bhkType(propertyDetailsDTO.getBhkType())
                    .availability(scheduleDTO.getAvailability())
                    .startTime(scheduleDTO.getStartTime())
                    .endTime(scheduleDTO.getEndTime())
                    .propertySpecs(propertySpecs)
                    .posted_on(new Date())
                    .property_rent(rentalDetailsDTO.getExpectedRent())
                    .property_maintenance(rentalDetailsDTO.getExpectedMaintenance())
                    .preferred_tenant_type(rentalDetailsDTO.getPreferredTenants())
                    .rent_negotiable(rentalDetailsDTO.isRentNegotiable())
                    .expected_Deposit(rentalDetailsDTO.getExpectedDeposit())
                    .who_will_show_the_property(amenitiesDTO.getShowProperty())
                    .showProperty_contact(amenitiesDTO.getContactNumber())
                    .propertyFor(rentalDetailsDTO.getPropertyAvailable())
                    .available_from(rentalDetailsDTO.getAvailableFrom())
                    .build();
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to convert json");

        }
        return propertyRent;
    }

    @Override
    public String saveProperty(String jsonProperty, List<MultipartFile> images, String loggedInUser) {

        PropertyRent propertyRent = convertJsonToProperty(jsonProperty);
        BaseTimeStamp baseTimeStamp = BaseTimeStamp.builder().created_by(loggedInUser).created_on(new Date()).build();
        propertyRent.setBaseTimeStamp(baseTimeStamp);
        List<Images> list_of_Images = convertMulipartToListOfImages(images, loggedInUser);
        propertyRent.setImageMap(list_of_Images);

        String folderName = propertyRent.getName() + "___" + UUID.randomUUID();

        CloudOperations cloudOperations = new CloudOperations(s3Client, bucketName);
        String folderUrl = cloudOperations.createFolder(folderName);

        List<String> imagesUrls = cloudOperations.uploadImagesToFolder(list_of_Images, folderName);
        propertyRent.setFolderUrl(folderUrl);
        propertyRent.setFolderName(folderName);
        propertyRent.setImagesUrls(imagesUrls);


        propertyForRentRepository.save(propertyRent);
        return folderUrl;


    }


}
