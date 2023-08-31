package com.LoFor1t.WeatherApp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private float latitude;

    private float longitude;

    @Transient
    private float temperature;

    @Transient
    private String weather;


    public Location(String name, User user, float latitude, float longitude, float temperature, String weather) {
        this.name = name;
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature = temperature;
        this.weather = weather;
    }
}
