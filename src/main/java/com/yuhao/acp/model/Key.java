package com.yuhao.acp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Dictionary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Key {

    @Id
    private String myKey;
    private String myValue;
}
