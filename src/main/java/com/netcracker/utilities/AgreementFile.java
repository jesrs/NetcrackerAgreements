package com.netcracker.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Manage the creation and reading of the agreement's files
 * Jesús Rodríguez Salazar jesrs@yahoo.com
 * v1.0
 * Date: 27/07/2021
 */
public class AgreementFile {

	private static final Logger logger = LoggerFactory.getLogger(AgreementFile.class);
	private static String DIRECTORY_NAME = "Agreements";

	//Creates the agreement file
	public void createAgreementFile(Agreement agreement) throws IOException {

		File directory = new File(DIRECTORY_NAME);

		// If the directory does not exist create it
		if (!directory.exists()) {
			directory.mkdir();
		}

		// Changes the / to - not to cause error writing the file
		File file = new File(DIRECTORY_NAME + "/" + agreement.getName().replace("/", "-") + ".txt");

		// Creates the file if it does not exist
		if (!file.exists()) {
			logger.debug("The file does not exist");
			boolean createdFile = file.createNewFile();
			if (!createdFile) {
				throw new IOException("Error creating the file");
			}
		}

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {

			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);

			// Writes the agreeement that is in the header
			bufferedWriter
					.write("Agreement's name: " + agreement.getName() + ", Signed by: " + agreement.getSignedBy());
			bufferedWriter.newLine();

			// Writes the products
			writeProducts(agreement.getProducts(), bufferedWriter, 1);

		} catch (IOException ioE) {
			throw new IOException("Error writing the file");
		} finally {
			if (bufferedWriter != null)
				bufferedWriter.close();
			if (fileWriter != null)
				fileWriter.close();
		}

	}

	private void writeProducts(List<Product> products, BufferedWriter bufferedWriter, int level) throws IOException {

		for (Product product : products) {

			// for each level, it writes -> that allows us to know which products are nested
			for (int i = 0; i < level; i++) {
				bufferedWriter.write("->");
			}
			bufferedWriter.write("Product name: " + product.getName() + ", price: " + product.getPrice());
			bufferedWriter.newLine();

			// if we have sub products, we write them to the file and send the next value of the level
			if (product.getProducts() != null && !product.getProducts().isEmpty()) {
				writeProducts(product.getProducts(), bufferedWriter, level + 1);
			}

		}

	}

	// Validates if the agreement's file exist
	public boolean validateAgreementExist(String agreementName) {
		File file = new File(DIRECTORY_NAME + "/" + (agreementName.replace("/", "-") + ".txt"));
		return file.exists();
	}

	// Reads the agreement's file
	public Agreement readAgreement(String agreementName) throws IOException {
		
		File file = new File(DIRECTORY_NAME + "/" + (agreementName.replace("/", "-") + ".txt"));
		Agreement agreement = new Agreement();

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);

			// Reads the first line, as it is agreement sets its values to the agreement object
			String agreementString = bufferedReader.readLine();

			// divide the string 
			List<String> agreementDefinition = ManageString.divideString(agreementString, ",");
			List<String> signedByDefinition = ManageString.divideString(agreementDefinition.get(1), ":");

			// set the obtained values
			agreement.setName(agreementName);
			agreement.setSignedBy(signedByDefinition.get(1).trim());

			// reads the products of the agreement
			List<Product> products = new ArrayList<>();
			readProducts(agreement, bufferedReader, 1, products);

			agreement.setProducts(products);
		} catch (IOException ioE) {
			throw new IOException("Error reading the file");
		} finally {
			if (bufferedReader != null)
				bufferedReader.close();
			if (fileReader != null)
				fileReader.close();
		}
		return agreement;

	}

	// Read the products of the agreement no matter their levels
	private String readProducts(Object parent, BufferedReader bufferedReader, int level, List<Product> products)
			throws IOException {
		String line;
		
		// while exist line to analyze 
		while ((line = bufferedReader.readLine()) != null) {
			
			// obtains the "level" of the reading line
			int levelOfLine = obtainLevel(line);
			
			// if it has a level
			if (levelOfLine != 0) {
				
				// if it has a nested level
				if (levelOfLine == (level + 1)) {
					
					// reads the product and sets the values to a nested level of products
					List<Product> productsNextLevel = new ArrayList<>();
					Product lastProduct = products.get(products.size() - 1);
					Product product = obtainProduct(line, lastProduct);
					productsNextLevel.add(product);
					lastProduct.setProducts(productsNextLevel);

					// sends to analyze the next lines and sends it to the next level, gets the line that not corresponds to the next level
					line = readProducts(lastProduct, bufferedReader, level + 1, productsNextLevel);
					levelOfLine = obtainLevel(line);

				}
				// if the line is of the same level, adds the product to the actual level
				if (levelOfLine == level) {
					Product product = obtainProduct(line, parent);
					products.add(product);
				}
				
				// if the line has no level or is a parent level, returns it to the previos iteration
				if ((levelOfLine == (level - 1)) && levelOfLine != 0) {
					return line;
				}
			}
		}
		return line;
	}

	// Obtains the level of the line depending of the quantity of arrows "->"
	private int obtainLevel(String line) {
		String strFind = "->";

		if (null == line) {
			return 0;
		}

		int count = 0;
		int fromIndex = 0;

		// counts the occurrences of arrows 
		while ((fromIndex = line.indexOf(strFind, fromIndex)) != -1) {
			logger.debug("Found at index: {}",fromIndex);
			count++;
			fromIndex++;
		}
		logger.debug("Total occurrences: {}",count);
		return count;
	}

	// divides the line to obtain the values of the products
	private Product obtainProduct(String line, Object parent) {
		Product product = new Product();
		List<String> productDefinition = ManageString.divideString(line, ",");
		List<String> nameProductDefinition = ManageString.divideString(productDefinition.get(0), ":");
		List<String> priceProductDefinition = ManageString.divideString(productDefinition.get(1), ":");
		product.setName(nameProductDefinition.get(1).trim());
		product.setPrice(Double.parseDouble(priceProductDefinition.get(1).trim()));
		product.setParent(parent.toString());
		return product;
	}

}
