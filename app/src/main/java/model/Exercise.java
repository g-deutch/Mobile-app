package model;

public class Exercise {
    private String name;
    private String muscleGroup;
    private String previewScr;

    public Exercise() {
        // Default constructor required for calls to DataSnapshot.getValue(Exercise.class)
    }

    public Exercise(String name, String muscleGroup, String previewScr) {
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.previewScr = previewScr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public String getPreviewScr() {
        return previewScr;
    }

    public void setPreviewScr(String previewScr) {
        this.previewScr = previewScr;
    }



}
