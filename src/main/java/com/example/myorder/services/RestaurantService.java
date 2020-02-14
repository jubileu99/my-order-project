package com.example.myorder.services;

import com.example.myorder.api.dtos.CreateRestaurantDto;
import com.example.myorder.api.dtos.RestaurantResponseDto;
import com.example.myorder.entities.Restaurant;
import com.example.myorder.exception.NotFoundExpection;
import com.example.myorder.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public void createRestaurant(CreateRestaurantDto createRestaurantDto){
        // TODO: validacoes

        Restaurant restaurant = new Restaurant()
                .setEmail(createRestaurantDto.getEmail())
                .setName(createRestaurantDto.getName())
                .setPhone(createRestaurantDto.getPhone());

        restaurantRepository.save(restaurant);
    }

    public RestaurantResponseDto getById(Integer id){

        Restaurant restaurant = findById(id);

        return new RestaurantResponseDto()
                .setId(restaurant.getId())
                .setEmail(restaurant.getEmail())
                .setName(restaurant.getName())
                .setPhone(restaurant.getPhone());

    }

    public Restaurant findById(Integer id){
        Optional<Restaurant> optinal = restaurantRepository.findById(id);
        if(!optinal.isPresent()){
            throw new NotFoundExpection("Resturante nao encontrado");
        }

        return optinal.get();

    }

}
