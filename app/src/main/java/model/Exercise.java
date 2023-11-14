package model;

public class Exercise {
    private String name;
    private String muscle;
    private String previewSrc;

    public Exercise() {
        // Default constructor required for calls to DataSnapshot.getValue(Exercise.class)
    }

    public Exercise(String name, String muscle, String previewSrc) {
        this.name = name;
        this.muscle = muscle;
        this.previewSrc = previewSrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getPreviewSrc() {
        return previewSrc;
    }

    public void setPreviewSrc(String previewSrc) {
        this.previewSrc = previewSrc;
    }



}
