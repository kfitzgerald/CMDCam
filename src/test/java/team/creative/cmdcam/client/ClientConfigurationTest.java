package team.creative.cmdcam.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import team.creative.cmdcam.common.util.CamPath;
import team.creative.cmdcam.common.util.CamPoint;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientConfigurationTest {

    @Test
    void saveFileAndLoadFile() {

        ClientConfiguration config = new ClientConfiguration();
        List<CamPoint> points = new ArrayList<>();
        points.add(new CamPoint(-10.0, 64.0, 20.0, -64.0, 36.0, 0.0, 90.0));
        points.add(new CamPoint(20.0, 64.0, -10.0, -64.0, -36.0, 10.0, 90.0));
        CamPath path1 = new CamPath(0, 2000, "default", "hermite", null, points, 1.0);
        config.paths.put("test", path1);

        Path path = Path.of("test.json");
        config.saveFile(path);
        assertEquals(1, config.paths.size());
        assertEquals(2, config.paths.get("test").points.size());

        ClientConfiguration freshConfig = new ClientConfiguration();
        assertEquals( 0, freshConfig.paths.size());
        freshConfig.loadFile(path);
        assertEquals( 1, freshConfig.paths.size());
        assertEquals(2, freshConfig.paths.get("test").points.size());

    }
}