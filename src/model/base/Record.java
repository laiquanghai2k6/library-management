package model.base;

import java.util.UUID;

public abstract class Record {
    protected UUID id;
    protected UUID user_id;

    public Record() {
    }

    public Record(UUID id, UUID user_id) {
        this.id = id;
        this.user_id = user_id;
        
    }

    public UUID getId() {
        return id;
    }

    public UUID getUser_id() {
        return user_id;
    }

 
    public void setId(UUID id) {
        this.id = id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

   
}
