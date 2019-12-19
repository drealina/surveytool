package de.hsba.bi.Umfrage.survey;



import javax.persistence.*;


@Entity
public class Survey {

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String description;

    @Basic(optional = false)
    private Boolean active;

    @Basic(optional = false)
    private Long userId;




    //Getter und Setter
    public Long getId() {
        return id;
    }

   public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }


    //Default Konstruktor
    public Survey(){

    }

    // Konstruktor
    public Survey(String name, String description, Boolean active, Long userId) {
        this.name = name;
        this.description = description;
        this.active = active;
        this.userId = userId;
    }

    public Survey(String name, String description) {
    }

    // to String
    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", userId=" + userId +
                '}';
    }

}



