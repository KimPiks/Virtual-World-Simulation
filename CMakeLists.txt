cmake_minimum_required(VERSION 3.22)
project(VirtualWorld)

set(CMAKE_CXX_STANDARD 17)
set(SOURCE_FILES main.cpp Saving/Saving.cpp)

add_executable(VirtualWorld main.cpp
        Organism/Organism.cpp
        Organism/Organism.h
        Human/Human.cpp
        Human/Human.h
        World/World.cpp
        World/World.h
        Position.h
        Animal/Animal.cpp
        Animal/Animal.h
        GUI/GUI.cpp
        GUI/GUI.h
        Wolf/Wolf.cpp
        Wolf/Wolf.h
        Settings.h
        Sheep/Sheep.cpp
        Sheep/Sheep.h
        Fox/Fox.cpp
        Fox/Fox.h
        Turtle/Turtle.cpp
        Turtle/Turtle.h
        Antelope/Antelope.cpp
        Antelope/Antelope.h
        Plant/Plant.cpp
        Plant/Plant.h
        Grass/Grass.cpp
        Grass/Grass.h
        Milkweed/Milkweed.cpp
        Milkweed/Milkweed.h
        Guarana/Guarana.cpp
        Guarana/Guarana.h
        NightshadeBerries/NightshadeBerries.cpp
        NightshadeBerries/NightshadeBerries.h
        SosnowskyHogweed/SosnowskyHogweed.cpp
        SosnowskyHogweed/SosnowskyHogweed.h
        Saving/Saving.cpp
        Saving/Saving.h)