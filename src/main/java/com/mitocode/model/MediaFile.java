package com.mitocode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MediaFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFile;

    @Column(length = 50, nullable = false)
    private String filename;

    @Column(length = 20, nullable = false)
    private String fileType;

    @Column(name = "content", nullable = false)
    private byte[] value;
}
