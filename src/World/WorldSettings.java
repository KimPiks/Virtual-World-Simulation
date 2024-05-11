package World;

public record WorldSettings(WorldType worldType, int width, int height) {
    public WorldSettings {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be greater than 0");
        }
    }
}
