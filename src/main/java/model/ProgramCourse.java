package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
@Entity
@Table(name = "Program_Course")
public class ProgramCourse {

    @EmbeddedId
    private ProgramCourseId id;

    @ManyToOne
    @JoinColumn(name = "fk_program", insertable = false, updatable = false)
    private Program program;

    @ManyToOne
    @JoinColumn(name = "fk_course", insertable = false, updatable = false)
    private Course course;

    private int position;

    public ProgramCourse(Program p, Course c, int position) {
        // create primary key
        this.id = new ProgramCourseId(p.getId(), c.getId());

        // initialize attributes
        this.course = c;
        this.program = p;
        this.position = position;

        // update relationships to assure referential integrity
        p.getCourses().add(this);
        c.getPrograms().add(this);
    }

    @Embeddable
    public static class ProgramCourseId implements Serializable {

        @Column(name = "fk_program")
        private Integer programId;

        @Column(name = "fk_course")
        private Integer courseId;

        public ProgramCourseId() {
        }

        public ProgramCourseId(int programId, int courseId) {
            this.programId = programId;
            this.courseId = courseId;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((programId == null) ? 0 : programId.hashCode());
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

            ProgramCourseId other = (ProgramCourseId) obj;

            if (programId == null) {
                if (other.programId != null)
                    return false;
            } else if (!programId.equals(other.programId))
                return false;

            if (courseId == null) {
                if (other.courseId != null)
                    return false;
            } else if (!courseId.equals(other.courseId))
                return false;

            return true;
        }
    }

    public ProgramCourse() {
    }

    public ProgramCourseId getId() {
        return id;
    }

    public Program getProgram() {
        return program;
    }

    public Course getCourse() {
        return course;
    }

    public int getPosition() {
        return position;
    }

    public void setId(ProgramCourseId id) {
        this.id = id;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
