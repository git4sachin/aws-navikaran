package com.capgemini.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.capgemini.utils.SaveFilePathToProperties;

@Controller
public class UploadController {

	private static String UPLOAD_FOLDER = SaveFilePathToProperties.getFilePath();

	@RequestMapping("/upload")
	public ModelAndView showUpload() {
		return new ModelAndView("upload");
	}

	@PostMapping("/upload")
	public ModelAndView fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		if (file.isEmpty()) {
			return new ModelAndView("status", "message", "Please select a file and try again");
		}
		try {
			// read and write the file to the slelected location-
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
			SaveFilePathToProperties.saveFileName(file.getOriginalFilename());
			SaveFilePathToProperties.saveFilePath("C:\\test\\");
			Files.write(path, bytes);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ModelAndView("reviews", "message", "File Uploaded sucessfully. Please wait for the output...");
	}
}