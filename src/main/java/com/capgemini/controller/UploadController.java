package com.capgemini.controller;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
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

	private static final String UPLOAD_SUCCESS = "File uploaded successfully, Please wait for the output..";
	private static final String UPLOAD_ERROR = "There was an error uploading the file, please check server logs for details";
	private static final String FILE_SELECT = "Please select the file and try again..";
	private static final String UPLOAD_FOLDER = FileSystems.getDefault().getPath("upload").normalize().toAbsolutePath()
			.toString();
	private static final String DOWNLOAD_FOLDER = FileSystems.getDefault().getPath("download").normalize().toAbsolutePath().toString();

	@RequestMapping("/upload")
	public ModelAndView showUpload() {
		System.out.println("Inside GET for Upload..");
		return new ModelAndView("upload");
	}

	@PostMapping("/upload")
	public ModelAndView fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
		
		String fileName = file.getOriginalFilename();
		String viewName = "";

		StringBuilder uploadStatusMessage = new StringBuilder();
		if (!file.isEmpty()) {
			try {
				
				File inputFolder = new File(UPLOAD_FOLDER);
				if (!inputFolder.exists()) {
					if (inputFolder.mkdir())
						System.out.println("Folder with name 'upload' is created at path: " + UPLOAD_FOLDER);
				} else {
					FileUtils.cleanDirectory(inputFolder);
				}
				
				File outputFolder = new File(DOWNLOAD_FOLDER);
				if (!outputFolder.exists()) {
					if (outputFolder.mkdir())
						System.out.println("Folder with name 'download' is created at path: " + DOWNLOAD_FOLDER);
				} else {
					FileUtils.cleanDirectory(inputFolder);
				}
				
				Path path = Paths.get(UPLOAD_FOLDER, fileName);
				byte[] bytes = file.getBytes();
				Files.write(path, bytes);
				
				SaveFilePathToProperties.saveFilePath(UPLOAD_FOLDER);
				SaveFilePathToProperties.saveFileName(fileName);
				uploadStatusMessage.append(UPLOAD_SUCCESS);
				viewName = "reviews";

			} catch (Exception e) {
				uploadStatusMessage.append(UPLOAD_ERROR);
				e.printStackTrace();
			}
		} else {
			uploadStatusMessage.append(FILE_SELECT);
			viewName = "status";
		}
		return new ModelAndView(viewName, "message", uploadStatusMessage);
	}

}