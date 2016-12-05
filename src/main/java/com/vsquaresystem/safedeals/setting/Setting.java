package com.vsquaresystem.safedeals.setting;

import java.util.Objects;

public class Setting {

    private Integer id;
    private SettingKey settingKey;
    private String settingValue;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SettingKey getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(SettingKey settingKey) {
        this.settingKey = settingKey;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Setting{" + "id=" + id + ", settingKey=" + settingKey + ", settingValue=" + settingValue + ", description=" + description + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.settingKey);
        hash = 41 * hash + Objects.hashCode(this.settingValue);
        hash = 41 * hash + Objects.hashCode(this.description);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Setting other = (Setting) obj;
        if (!Objects.equals(this.settingValue, other.settingValue)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.settingKey != other.settingKey) {
            return false;
        }
        return true;
    }
    
    
}
