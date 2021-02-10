package com.example.movieapp.domain.mappers

interface IMapper<FROM, TO> {

    fun map(item:FROM):TO
}