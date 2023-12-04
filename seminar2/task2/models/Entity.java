package seminar2.task2.models;


import seminar2.task2.Column;

import java.util.UUID;

@seminar2.task2.Entity
public class Entity {

    @Column(name = "id", primaryKey = true)
    private UUID id;

}
