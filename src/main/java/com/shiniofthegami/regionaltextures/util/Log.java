package com.shiniofthegami.regionaltextures.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

import org.bukkit.Bukkit;

public class Log {
	/**
	 * create a new log file if it does not exist already
	 * @param file name of the new file, excluding the ".log"
	 * @return true if file either exists already or has been successfully created, false if an error occured
	 */
	public static boolean createFile(String file){
		File f = new File(Bukkit.getPluginManager().getPlugin("RegionalTextures").getDataFolder(),file+".log");
		if(f.exists() && !f.isDirectory()){
			return true;
		}
		f.getParentFile().mkdir();
		
		try{
			f.createNewFile();
			FileWriter filewriter = new FileWriter(Bukkit.getPluginManager().getPlugin("RegionalTextures").getDataFolder() + "/" + file + ".log");
			try{
				filewriter.write("");
			}catch (Exception e){
				e.printStackTrace();
				return false;
			}finally{
				filewriter.flush();
				filewriter.close();
			}
			return true;
		}catch (Exception e){
			Bukkit.getLogger().log(Level.SEVERE, "Failed to create file " + file + ".log!");
			return false;
		}
	}
	/**
	 * Write an ArrayList of Strings to a file
	 * @param file filename to write to, excluding ".txt"
	 * @param text ArrayList containing the lines to write to the file
	 */
	public static void writeTextToFile(String file, ArrayList<String> text) throws IOException{
			File f = new File(Bukkit.getPluginManager().getPlugin("RegionalTextures").getDataFolder(),file+".log");
			FileOutputStream fos = new FileOutputStream(f);			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			
			for(String s : text){
				bw.write(s);
				bw.newLine();
			}
			
			bw.close();
			
	}
	/**
	 * parse a .txt file to an ArrayList containing the lines
	 * @param file filename excluding ".txt"
	 * @return the ArrayList parsed
	 */
	public static ArrayList<String> parseFile(String file){
		File f = new File(Bukkit.getPluginManager().getPlugin("RegionalTextures").getDataFolder() + "/" + file + ".log");
		ArrayList<String> output = new ArrayList<String>();
		try {
			Scanner input = new Scanner(f);
			while(input.hasNext()){
				output.add(input.nextLine());
			}
			input.close();
			return output;
		} catch (FileNotFoundException e) {
			Bukkit.getLogger().log(Level.SEVERE, "Failed to read file " + file + ".log");
			return null;
		}	
	}
}
