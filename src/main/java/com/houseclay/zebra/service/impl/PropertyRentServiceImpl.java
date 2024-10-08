package com.houseclay.zebra.service.impl;

//import com.amazonaws.services.mediastoredata.model.PutObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.houseclay.zebra.CloudOperations.CloudOperations;
import com.houseclay.zebra.dto.PropertyRentDTO;
import com.houseclay.zebra.dto.SaveAndUrlResponseDTO;
import com.houseclay.zebra.dto.ViewAllPropertiesDTO;
import com.houseclay.zebra.model.common.Address;
import com.houseclay.zebra.model.common.Amenities;
import com.houseclay.zebra.model.common.BaseTimeStamp;
import com.houseclay.zebra.model.common.PropertySpecs;
import com.houseclay.zebra.model.rental.Images;
import com.houseclay.zebra.model.rental.Owner;
import com.houseclay.zebra.model.rental.PropertyRent;
import com.houseclay.zebra.dto.ImageResponse;
import com.houseclay.zebra.repository.ImageRepository;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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



    // delete property for Database and CLOUD
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
                preferred_tenant_type(proRent.getPreferred_tenant_type()).created_by(proRent.getBaseTimeStamp().getCreated_by())
                        .created_on(proRent.getBaseTimeStamp().getCreated_on()).
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
            ViewAllPropertiesDTO viewAllPropertiesDTO= ViewAllPropertiesDTO.builder().property_id(p.getProperty_id()).name(p.getName()).title(p.getTitle()).build();
            allProperties.add(viewAllPropertiesDTO);
        }
        return allProperties;
    }





    public PropertyRent viewSpecificProperty(UUID propertyId) throws FileNotFoundException {
        Optional<PropertyRent> propertyRent = propertyForRentRepository.findById(propertyId);
            if (!propertyRent.isPresent()) {
                throw new FileNotFoundException("The file not found !!");
            }
        return propertyRent.get();
    }







    public List<Images> convertMulipartToListOfImages(List<MultipartFile> images){
        List<Images> list_of_Images = new ArrayList<Images>();
        for (MultipartFile f : images) {
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
return list_of_Images;
    }




    @Override
    public String saveProperty(String jsonProperty, List<MultipartFile> images) {
        PropertyRent propertyRent=convertStringToObject(jsonProperty);
        BaseTimeStamp baseTimeStamp = BaseTimeStamp.builder().created_by("SYSTEM").created_on(new Date()).build();
        propertyRent.setBaseTimeStamp(baseTimeStamp);
            List<Images>list_of_Images=convertMulipartToListOfImages(images);
            propertyRent.setImageMap(list_of_Images);

            String folderName=propertyRent.getName()+"___"+UUID.randomUUID();

            CloudOperations cloudOperations=new CloudOperations(s3Client, bucketName);
            String folderUrl=cloudOperations.createFolder(folderName);

            cloudOperations.uploadImagesToFolder(list_of_Images, folderName);
            propertyRent.setFolderUrl(folderUrl);
            propertyRent.setFolderName(folderName);


            propertyForRentRepository.save(propertyRent);
            return folderUrl;
    }







    @Override
    public PropertyRent updatePropertyMultipartData(UUID propertyId, PropertyRent newProperty, List<MultipartFile> images) throws FileNotFoundException {
        Optional<PropertyRent>propertyRent=propertyForRentRepository.findById(propertyId);
        if(!propertyRent.isPresent()){
            throw new FileNotFoundException("File Not Found");
        }

        PropertyRent existingProperty=propertyRent.get();


        List<Images>newImages=convertMulipartToListOfImages(images);
        List<Images>oldImages=existingProperty.getImageMap();



        CloudOperations cloudOperations=new CloudOperations(s3Client, bucketName);
        cloudOperations.deleteAllImages(existingProperty.getFolderName(), oldImages);
        cloudOperations.uploadImagesToFolder(newImages, existingProperty.getFolderName());


        BaseTimeStamp baseTimeStamp=newProperty.getBaseTimeStamp();
        baseTimeStamp.setChanged_by("SYSTEM");
        baseTimeStamp.setChanged_on(new Date());
        existingProperty.getBaseTimeStamp().setChanged_on(new Date());
        existingProperty.getBaseTimeStamp().setChanged_by("SYSTEM");
        existingProperty.setName(newProperty.getName());
        existingProperty.setTitle(newProperty.getTitle());
        existingProperty.setOwner(newProperty.getOwner());
        existingProperty.setPropertySpecs(newProperty.getPropertySpecs());
        existingProperty.setPosted_on(newProperty.getPosted_on());
        existingProperty.setAvailable_from(newProperty.getAvailable_from());
        existingProperty.setProperty_rent(newProperty.getProperty_rent());
        existingProperty.setProperty_maintenance(newProperty.getProperty_maintenance());
        existingProperty.setPreferred_tenant_type(newProperty.getPreferred_tenant_type());

        existingProperty.setActive_status(false); // ------------------
        existingProperty.setBaseTimeStamp(baseTimeStamp);

        return propertyForRentRepository.save(existingProperty);
    }






    public PropertyRent convertStringToObject(String jsonProperty){
        PropertyRent propertyRent=new PropertyRent();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode propertyData = objectMapper.readTree(jsonProperty);
            JsonNode propertyDetails = propertyData.get("propertyDetails");
            JsonNode localityDetails = propertyData.get("localityDetails");
            JsonNode rentalDetails = propertyData.get("rentalDetails");
            JsonNode ownerDetails = propertyData.get("ownerDetails");
            JsonNode amenities = propertyData.get("amenities");
            JsonNode schedule = propertyData.get("schedule");
            JsonNode amenitiesMap = amenities.get("amenities");

            Owner ownerObject = Owner.builder().owner_id(UUID.randomUUID()).owner_name(ownerDetails.get("firstName").asText() + " " + ownerDetails.get("lastName").asText()).owner_contact(ownerDetails.get("contactNumber").asText()).owner_email(ownerDetails.get("Email").asText()).availability(schedule.get("Availability").asText()).startTime(schedule.get("StartTime").asText()).endTime(schedule.get("EndTime").asText()).build();
            Address address = Address.builder().addr_id(UUID.randomUUID()).city(localityDetails.get("city").asText()).street_name(localityDetails.get("Landmark").asText()).property_name(localityDetails.get("locality").asText()).build();

            Amenities amenitiesObject = Amenities.builder().lift(amenitiesMap.get("lift").asBoolean()).
                    club_house(amenitiesMap.get("clubHouse").asBoolean()).
                    intercom(amenitiesMap.get("intercom").asBoolean()).
                    swimming_pool(amenitiesMap.get("swimmingPool").asBoolean()).
                    children_play_area(amenitiesMap.get("childrenPlayArea").asBoolean()).
                    fire_safety(amenitiesMap.get("fireSafety").asBoolean()).
                    security(amenitiesMap.get("security").asBoolean()).
                    park(amenitiesMap.get("park").asBoolean()).
                    rain_water_harvesting(amenitiesMap.get("rainwaterHarvesting").asBoolean()).
                    sewage_treatment_plant(amenitiesMap.get("sewageTreatmentPlant").asBoolean()).
                    housekeeping(amenitiesMap.get("housekeeping").asBoolean()).
                    power_backup(amenitiesMap.get("powerBackup").asBoolean())
                    .internet_services(amenitiesMap.get("internetServices").asBoolean())
                    .servant_room(amenitiesMap.get("servantRoom").asBoolean())
                    .gas_pilpeline(amenitiesMap.get("gasPipeline").asBoolean())
                    .shopping_centre(amenitiesMap.get("shoppingCentre").asBoolean())
                    .build();

            PropertySpecs propertySpecs = PropertySpecs.builder().full_address(address)
                    .type(propertyDetails.get("type").asText())
                    .carpet_area(propertyDetails.get("builtupArea").asDouble())
                    .bath(amenities.get("Bathroom").asInt())
                    .balcony(amenities.get("Balcony").asInt())
                    .parking(rentalDetails.get("parking").asText())
                    .facing(propertyDetails.get("facing").asText())
                    .property_age(propertyDetails.get("propertyAge").asText())
                    .furnishing(rentalDetails.get("furniture").asText())
                    .city(localityDetails.get("city").asText())
                    .description(rentalDetails.get("Description").asText())
                    .amenitiesMap(amenitiesObject)
                    .beds(0)  // required--------------------------------
                    .totalFloors(propertyDetails.get("totalFloor").asInt())
                    .floor(propertyDetails.get("floor").asInt())
                    .bhkType(propertyDetails.get("bhkType").asText())
                    .build();


// name , title need to be added in ui
            propertyRent = PropertyRent.builder()
                    .property_id(UUID.randomUUID())
                    .name("-------")
                    .name(propertyDetails.get("name").asText())
                    .title("---------")
                    .owner(ownerObject)
                    .propertySpecs(propertySpecs)
                    .posted_on(new Date())
                    .property_rent(rentalDetails.get("ExpectedRent").asDouble())
                    .property_maintenance(rentalDetails.get("monthlyMaintenence").asDouble())
                    .preferred_tenant_type(rentalDetails.get("preferredTenanats").asText())
                    .active_status(false)
                    .gym(amenities.get("Gym").asText())
                    .expected_Deposit(rentalDetails.get("ExpectedDeposit").asDouble())
                    .who_will_show_the_property(amenities.get("showProperty").asText())
                    .showProperty_contact(amenities.get("contactNumber").asText())
                    .propertyFor(rentalDetails.get("propertyAvailable").asText())
                    .gatedSecurity(amenities.get("GatedSecurity").asText())
                    .build();
            String s = rentalDetails.get("AvailableFrom").asText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            propertyRent.setAvailable_from(sdf.parse(s));
        }catch (Exception e) {
            e.printStackTrace();

        }

                return propertyRent;
    }


}
