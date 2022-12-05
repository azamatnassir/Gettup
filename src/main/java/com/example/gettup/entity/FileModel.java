package com.example.gettup.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;

@Entity
@Table(name = "t_file")
@Setter
@Getter
@NoArgsConstructor
public class FileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    private byte[] data;

    public FileModel(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public String generateBase64Image()
    {
        return Base64.encodeBase64String(this.getData());
    }
}
