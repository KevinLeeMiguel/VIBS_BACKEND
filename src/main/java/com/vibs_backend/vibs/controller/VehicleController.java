package com.vibs_backend.vibs.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.vibs_backend.vibs.dao.VehicleImageDao;
import com.vibs_backend.vibs.domain.Subscription;
import com.vibs_backend.vibs.domain.Vehicle;
import com.vibs_backend.vibs.domain.VehicleImage;
import com.vibs_backend.vibs.service.ISubscriptionService;
import com.vibs_backend.vibs.service.IVehicleService;
import com.vibs_backend.vibs.utilities.ResponseBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    private IVehicleService vehicleService;
    @Autowired
    private ISubscriptionService sService;
    @Autowired
    private VehicleImageDao viService;

    /**
     * Method to create a new Vehicle
     * 
     * @param ic
     * @param request
     * @return
     */

    @PostMapping(value = "/save/customer/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody Vehicle ic, @PathVariable String id, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            String username = request.getHeader("doneBy");
            String ownerName = request.getHeader("names");
            ic.setDoneBy(username);
            ic.setLastUpdatedAt(new Date());
            ic.setLastUpdatedBy(username);
            ic.setOwnerReferenceId(id);
            ic.setOwnerReferenceEmail(username);
            ic.setOwnerReferenceName(ownerName);
            Vehicle icd = vehicleService.create(ic);
            rs.setCode(200);
            rs.setDescription("Saved successfully");
            rs.setObject(icd);
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error occurred while saving the  Vehicle");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @PostMapping(value = "/saveWithFile/customer/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createWithFile(@RequestParam Map<String, String> ic,
            @RequestParam("file") MultipartFile file, @PathVariable String id, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            String username = request.getHeader("doneBy");
            String ownerName = request.getHeader("names");
            Vehicle v = new Vehicle();
            Object o = upLoad(file,v.getId());
            if (o != null) {
                v.setDocument(o.toString());
                v.setDocumentName(file.getOriginalFilename());
                v.setDoneBy(username);
                v.setMake(ic.get("make"));
                v.setModel(ic.get("model"));
                v.setOwnerReferenceEmail(username);
                v.setOwnerReferenceId(id);
                v.setOwnerReferenceName(ownerName);
                v.setPlateNo(ic.get("plateNo"));
                v.setSeats(Integer.parseInt(ic.get("seats")));
                v.setVin(ic.get("vin"));
                v.setYear(Long.parseLong(ic.get("year")));
                Vehicle icd = vehicleService.create(v);
                rs.setCode(200);
                rs.setDescription("Saved successfully");
                rs.setObject(icd);
            } else {
                rs.setCode(500);
                rs.setDescription("Error while uploading file, try again");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error occurred while saving the  Vehicle");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    /**
     * method to get all companies
     * 
     * @return
     */
    @GetMapping(value = "")
    public ResponseEntity<Object> getVehicles() {
        ResponseBean rs = new ResponseBean();
        try {
            List<Vehicle> li = vehicleService.findAll();
            rs.setCode(200);
            rs.setDescription("");
            rs.setObject(li);
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error occurred while fetching  Companies");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @GetMapping(value = "/all/customer/{id}")
    public ResponseEntity<Object> getVehiclesByCustomerId(@PathVariable String id) {
        ResponseBean rs = new ResponseBean();
        try {
            List<Vehicle> li = vehicleService.findAllByOwnerId(id);
            rs.setCode(200);
            rs.setDescription("");
            rs.setObject(li);
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error occurred while fetching  Companies");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    // /**
    // * Method to delete an Vehicle
    // *
    // * @param id
    // * @return
    // */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteVehicle(@PathVariable String id, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        String username = request.getHeader("doneBy");
        try {
            Optional<Vehicle> ic = vehicleService.findById(id);
            if (ic.isPresent()) {
                ic.get().setLastUpdatedAt(new Date());
                ic.get().setLastUpdatedBy(username);
                vehicleService.delete(ic.get());
                rs.setCode(200);
                rs.setDescription("success");
            } else {
                rs.setCode(404);
                rs.setDescription("Vehicle not found");
                rs.setObject(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("An Error occurred , please contact administrator");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    /**
     * method to update
     * 
     * @param ic
     * @param request
     * @return
     */
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateVehicle(@PathVariable String id, @RequestBody Vehicle ic,
            HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            String username = request.getHeader("doneBy");
            Optional<Vehicle> ico = vehicleService.findById(id);
            if (ico.isPresent()) {
                ic.setDoneBy(username);
                ic.setId(ico.get().getId());
                ic.setLastUpdatedAt(new Date());
                ic.setLastUpdatedBy(username);
                vehicleService.update(ic);
                rs.setCode(200);
                rs.setDescription("Updated successfully");
                rs.setObject(ic);
            } else {
                rs.setCode(404);
                rs.setDescription("The Vehicle you are trying to update was not found");
                rs.setObject(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error occurred while saving the  Vehicle");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);

    }

    @PutMapping(value = "/update/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addVehicleImages(@PathVariable String id, @RequestParam("file1") MultipartFile image1,
            @RequestParam("file2") MultipartFile image2, @RequestParam("file3") MultipartFile image3,
            HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<Vehicle> ico = vehicleService.findById(id);
            if (ico.isPresent()) {
                Object u1 = upLoadCarImage(image1, ico.get().getId());
                Object u2 = upLoadCarImage(image2, ico.get().getId());
                Object u3 = upLoadCarImage(image3, ico.get().getId());
                if (u1 != null && u2 != null && u3 != null) {
                    VehicleImage vi1 = new VehicleImage();
                    vi1.setName(image1.getOriginalFilename());
                    vi1.setPath(u1.toString());
                    vi1.setVehicle(ico.get());

                    VehicleImage vi2 = new VehicleImage();
                    vi2.setName(image2.getOriginalFilename());
                    vi2.setPath(u2.toString());
                    vi2.setVehicle(ico.get());

                    VehicleImage vi3 = new VehicleImage();
                    vi3.setName(image3.getOriginalFilename());
                    vi3.setPath(u1.toString());
                    vi3.setVehicle(ico.get());

                    viService.save(vi1);
                    viService.save(vi2);
                    viService.save(vi3);
                    rs.setCode(200);
                    rs.setDescription("Images added successfully");
                } else {
                    rs.setCode(500);
                    rs.setDescription("image upload failed");
                }

            } else {
                rs.setCode(404);
                rs.setDescription("The Vehicle you are trying to update was not found");
                rs.setObject(null);
            }
        } catch (Exception e) {
            rs.setCode(500);
            rs.setDescription("Error occurred while saving the  Vehicle");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<Vehicle> v = vehicleService.findById(id);
            if (v.isPresent()) {
                rs.setCode(200);
                rs.setDescription("success");
                rs.setObject(v);
            } else {
                rs.setCode(404);
                rs.setDescription("Vehicle not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(300);
            rs.setDescription("Error occured");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @GetMapping(value = "/details/{id}")
    public ResponseEntity<Object> DetailsById(@PathVariable String id) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<Vehicle> v = vehicleService.findById(id);
            if (v.isPresent()) {
                List<Subscription> li = sService.findAllByVehicle(v.get().getId());
                Map<Object, Object> map = new HashMap<>();
                map.put("vehicle", v.get());
                map.put("subscription", li);
                rs.setCode(200);
                rs.setDescription("success");
                rs.setObject(map);
            } else {
                rs.setCode(404);
                rs.setDescription("vehicle not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(300);
            rs.setDescription("Error occured");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @RequestMapping(path = "/documents/download/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(String param, @PathVariable("uuid") String uuid) throws IOException {

        // SubscriptionContract doc = subcaDao.getOne(uuid);
        Optional<Vehicle> v = vehicleService.findById(uuid);

        String filePath = v.get().getDocument();
        File file = new File(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "inline; filename=" + v.get().getDocumentName());
        Path path = Paths.get(filePath);
        byte[] b = Files.readAllBytes((path));
        ByteArrayInputStream bis = new ByteArrayInputStream(b);
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(new InputStreamResource(bis));
    }

    @RequestMapping(path = "/images/download/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadImage(String param, @PathVariable("uuid") String uuid) throws IOException {

        // SubscriptionContract doc = subcaDao.getOne(uuid);
        Optional<VehicleImage> v = viService.findById(uuid);

        String filePath = v.get().getPath();
        File file = new File(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "inline; filename=" + v.get().getName());
        Path path = Paths.get(filePath);
        byte[] b = Files.readAllBytes((path));
        ByteArrayInputStream bis = new ByteArrayInputStream(b);
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(new InputStreamResource(bis));
    }

    public Object upLoad(MultipartFile file, String id) {
        try {
            String parent = "VIBS_FILES";
            File f = new File(parent);
            String filename = file.getOriginalFilename();
            if (!f.exists())
                f.mkdir();
            File sub = new File(f, "Carte_Jaunes");
            if (!sub.exists())
                sub.mkdir();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(sub.getPath() + "/" + id + '/' + filename);
            Files.write(path, bytes);
            return path;
        } catch (Exception e) {
            return null;
        }

    }

    public Object upLoadCarImage(MultipartFile file, String vehicleUuid) {
        try {
            String parent = "VIBS_FILES";
            File f = new File(parent);
            String filename = file.getOriginalFilename();
            if (!f.exists())
                f.mkdir();
            File sub = new File(f, "vehicles");
            if (!sub.exists())
                sub.mkdir();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(sub.getPath() + "/" + vehicleUuid + '/' + filename);
            Files.write(path, bytes);
            return path;
        } catch (Exception e) {
            return null;
        }

    }
}