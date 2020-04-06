package com.seller.panel.model;

import com.seller.panel.enums.SkuType;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "skus")
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class Skus extends BaseEntity {

    @Type(type = "list-array")
    @Column(columnDefinition = "TEXT[]")
    private List<String> categories;
    @Enumerated
    private SkuType type;
    private String code;
    private String name;
    private String title;
    private String description;
    private String base_uom;

}
