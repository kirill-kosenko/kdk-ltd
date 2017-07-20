package kdk.ltd.site.root.entities;

import javax.persistence.*;
import java.time.LocalDateTime;


@MappedSuperclass
public abstract class PersistableObjectAudit extends PersistableObject {
    @Column(name = "insert_ts")
    private LocalDateTime insertTimestamp;

    @Column(name = "update_ts")
    private LocalDateTime updateTimestamp;

    @Column(name = "user_name")
    private String username;

    @SuppressWarnings("unused")
    @PrePersist
    private void onIsert() {
        this.insertTimestamp = LocalDateTime.now();
        this.updateTimestamp = this.insertTimestamp;
    }

    @SuppressWarnings("unused")
    @PreUpdate
    private void onUpdate() {
        this.updateTimestamp = LocalDateTime.now();
    }

    public LocalDateTime getInsertTimestamp() {
        return insertTimestamp;
    }

    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    protected void setInsertTimestamp(LocalDateTime insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    protected void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
