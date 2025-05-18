package com.citizen.engagement_system_be.fileHandling;

import com.citizen.engagement_system_be.enums.EFileSizeType;
import com.citizen.engagement_system_be.enums.EFileStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "files", uniqueConstraints = {@UniqueConstraint(columnNames = "path")})
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Transient
    private String url;

    @Column(name = "size")
    private int size;

    @Column(name = "size_type")
    @Enumerated(EnumType.STRING)
    private EFileSizeType sizeType;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EFileStatus status;

    public File(String directory, String fileName, String extension, String fileBaseName) {
        this.name = fileName;
        this.path = directory + "/" + fileName;
        this.type = extension;
    }

    public String getUrl() {
        return "/files/load-file/" + name;
    }

}
