package com.main.tema_c1_2.service;

import com.main.tema_c1_2.entity.Contact;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

@Service
public class ContactService {
    private static int id=0;
    private static String fileName="/Users/nicudoran/TFP/M4/Teme/C1_2/agenda.txt";

    public void writeContactDetailsToFile(Contact contact){
    try{
        FileWriter fileWriter=new FileWriter(fileName,true);
        fileWriter.write(++id+";"+contact.getFirstName()+";"+contact.getLastName()+";"+contact.getPhoneNumber()+"\n");
        fileWriter.close();
    }catch(IOException e){};
}
public Object getAll(){

    try{
        List<String> contacts=new ArrayList<>();
        List<Object> result=new ArrayList<>();
        String line;
        BufferedReader br=new BufferedReader(new FileReader(fileName));
        while((line=br.readLine())!=null){
            contacts.add(line);
        }
        br.close();
        for (String row:contacts) {
            Map contact=new HashMap<>();
            String[] r=row.split(";");
           contact.put("id",r[0]);
           contact.put("firstName",r[1]);
           contact.put("lastName",r[2]);
           contact.put("phoneNumber",r[3]);
           result.add(contact);
       }
        return (result.isEmpty()? Collections.singletonMap("message","There are no contacts in the PhoneBook"):result);

    }catch(IOException e){
    return e.getMessage();
    }
}


public Map getByName(String name){
Object allContacts=this.getAll();
if (allContacts instanceof ArrayList<?>){
    List<Map<String,String>> contacts=(ArrayList<Map<String,String>>) allContacts;
    List<Map<String,String>>matchingContacts=new ArrayList<>();

    for (Map<String,String> contact:contacts){
        String firstName=contact.get("firstName");
        String lastName=contact.get("lastName");

        if (firstName.equalsIgnoreCase(name)||lastName.equalsIgnoreCase(name)){
            matchingContacts.add(contact);
        }
    }
    if (matchingContacts.isEmpty()){
        return Collections.singletonMap("message","Contact does not exists");
    } else{
        return Collections.singletonMap("contacts",matchingContacts);
    }
}else{
    return Collections.singletonMap("message","Something went wrong");
}
};
    public String deleteContact(int deleteId){
        boolean isDeleted=false;
        Map<String,String> deleted=null;
        Object allContacts=this.getAll();
        if (allContacts instanceof ArrayList<?>){
            List<Map<String,String>> contacts=(ArrayList<Map<String,String>>) allContacts;

            for (Map<String,String> contact:contacts){

                int contactId=parseInt(contact.get("id"));

                if (contactId==deleteId){
                    deleted=contact;
                    isDeleted=true;

                   }
            }
            if (isDeleted){
                contacts.remove(deleted);
                    try{
                        FileWriter fileWriter=new FileWriter(fileName,false);
                        for (Map<String,String> row:contacts){
                        fileWriter.write(parseInt(row.get("id"))+";"+row.get("firstName")+";"+row.get("lastName")+";"+row.get("phoneNumber")+"\n");
                        }

                        fileWriter.close();
                    }catch(IOException e){return e.getMessage();};
                return "The contact was successfully deleted.";
            } else{
                return "The contact was not deleted.";
            }
        }else{
            return "Something went wrong";
        }

    }
}



