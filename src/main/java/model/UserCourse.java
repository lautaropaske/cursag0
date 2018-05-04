package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Agustin Bettati
 * @version 1.0
 */

@Entity
public class UserCourse {

    @EmbeddedId
    private UserCourseId id;

    @ManyToOne
    @JoinColumn(name = "fk_user", insertable = false, updatable = false) //son false porque ya estan setteadas en UserCourseId
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_course", insertable = false, updatable = false)
    private Course course;

    private int progress;



    public UserCourse(User u, Course c, int progress) {
        // create primary key
        this.id = new UserCourseId(u.getId(), c.getId());

        // initialize attributes
        this.course = c;
        this.user = u;
        this.progress = progress;

        // update relationships to assure referential integrity
        u.getEnrolledCourses().add(this);
        c.getEnrolledStudents().add(this);
    }

    public void removeReferences(){
        this.course.getEnrolledStudents().remove(this);
        this.user.getEnrolledCourses().remove(this);
    }

    // Primary Key class
    @Embeddable
    public static class UserCourseId implements Serializable {

        @Column(name = "fk_user")
        private Integer userId;

        @Column(name = "fk_course")
        private Integer courseId;

        public UserCourseId() {

        }

        public UserCourseId(int userId, int courseId) {
            this.userId = userId;
            this.courseId = courseId;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((userId == null) ? 0 : userId.hashCode());
            result = prime * result
                    + ((courseId == null) ? 0 : courseId.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;

            UserCourseId other = (UserCourseId) obj;

            if (userId == null) {
                if (other.userId != null)
                    return false;
            } else if (!userId.equals(other.userId))
                return false;

            if (courseId == null) {
                if (other.courseId != null)
                    return false;
            } else if (!courseId.equals(other.courseId))
                return false;

            return true;
        }
    }

    public UserCourse() {
    }

    public UserCourseId getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Course getCourse() {
        return course;
    }

    public int getProgress() {
        return progress;
    }

    public void setId(UserCourseId id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
