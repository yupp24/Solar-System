package finalproject;
import com.sun.j3d.utils.applet.*;
import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.*;
import com.sun.j3d.utils.universe.*;

import javax.media.j3d.*;
import javax.vecmath.*;
import java.applet.*;
import java.awt.*;
import java.net.*;
public class solarSystem extends Applet{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainFrame(new solarSystem(), 1280, 900);
	}
	  private static BoundingSphere bounds = new BoundingSphere(new Point3d(0,0,0), Double.MAX_VALUE);

	    public void init() {
	        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();

	        Canvas3D cv = new Canvas3D(gc);
	        setLayout(new BorderLayout());
	        add(cv, BorderLayout.CENTER);

	        BranchGroup bg = createSceneGraph();

	        SimpleUniverse su = new SimpleUniverse(cv);
	        su.getViewer().getView().setBackClipDistance(1000);
	        Transform3D viewPlatformTransform = new Transform3D();
	        Transform3D t0 = new Transform3D();
	        t0.setTranslation(new Vector3d(0,0,30));
	        Transform3D t1 = new Transform3D();
	        t1.rotX(Math.toRadians(-30));
	        viewPlatformTransform.mul(t1, t0);
	        su.getViewingPlatform().getViewPlatformTransform().setTransform(viewPlatformTransform);

	        su.addBranchGraph(bg);
	    }

	    private BranchGroup createSceneGraph() {
	        BranchGroup root = new BranchGroup();

	        //Rendering in stars background
	        root.addChild(renderStars());

	        TransformGroup spin = new TransformGroup();
	        spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	        spin.setCapability(TransformGroup.ALLOW_BOUNDS_READ);
	        root.addChild(spin);

	        //Rotation
	        MouseRotate rotator = new MouseRotate(spin);
	        rotator.setSchedulingBounds(bounds);
	        spin.addChild(rotator);

	        //Translation
	        MouseTranslate translator = new MouseTranslate(spin);
	        translator.setSchedulingBounds(bounds);
	        spin.addChild(translator);

	        //Zoom
	        MouseZoom zoom = new MouseZoom(spin);
	        zoom.setSchedulingBounds(bounds);
	        spin.addChild(zoom);

	        // Sun \\
	        //Sun rotation speed
	        int sunOrbitTime = 4000;
	        TransformGroup ORB_Sun = createRotationTransformGroup(sunOrbitTime, false);
	        spin.addChild(ORB_Sun);

	        Sphere sun = new Sphere(0.8f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, 100);
	        Appearance sunAp = sun();

	        sun.setAppearance(sunAp);
	        ORB_Sun.addChild(sun);

	        // Mercury \\
	        //Mercury Orbit speed
	        TransformGroup ORB_Mercury = createRotationTransformGroup(1440, false); //24% of base speed
	        spin.addChild(ORB_Mercury);

	        TransformGroup TRA_Mercury = createTranslatingTransformGroup(1, 0,0);
	        ORB_Mercury.addChild(TRA_Mercury);

	        int rotationTimeMercury = 1500;
	        Sphere mercury = new Sphere(0.1f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, 100);

	        Appearance mercuryAp = mercury();
	        mercury.setAppearance(mercuryAp);

	        TransformGroup ROT_Mercury = createRotationTransformGroup(rotationTimeMercury, true);
	        ROT_Mercury.addChild(mercury);
	        TRA_Mercury.addChild(ROT_Mercury);

	        // Venus \\
	        //Orbit speed of Venus
	        TransformGroup ORB_Venus = createRotationTransformGroup(3660, false); //61% of base speed
	        spin.addChild(ORB_Venus);

	        //Translation of Venus
	        TransformGroup TRA_Venus = createTranslatingTransformGroup(2, 0,0);
	        ORB_Venus.addChild(TRA_Venus);

	        //Rotation speed of Venus
	        int rotationTimeVenus = 2000;
	        Sphere venus = new Sphere(0.2f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, 100);

	        Appearance venusAp = venus();
	        venus.setAppearance(venusAp);

	        TransformGroup ROT_Venus = createRotationTransformGroup(rotationTimeVenus, true);
	        ROT_Venus.addChild(venus);
	        TRA_Venus.addChild(ROT_Venus);


	        // Earth \\
	        //Earth orbit speed
	        TransformGroup ORB_Earth = createRotationTransformGroup(6000, false); //6000 will be the baseline rotation speed
	        spin.addChild(ORB_Earth);

	        //Translation of Earth
	        TransformGroup TRA_Earth = createTranslatingTransformGroup(3, 0, 0);
	        ORB_Earth.addChild(TRA_Earth);

	        //Rotation speed of Earth
	        int rotationTimeEarth = 1500;
	        Sphere earth = new Sphere(0.3f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, 100);

	        Appearance earthAp = earth();
	        earth.setAppearance(earthAp);

	        TransformGroup ROT_Earth = createRotationTransformGroup(rotationTimeEarth, true);
	        ROT_Earth.addChild(earth);
	        TRA_Earth.addChild(ROT_Earth);

	        // Moon \\
	        //Moon orbit speed
	        TransformGroup ORB_Moon = createRotationTransformGroup(840, false); //~27 days
	        TRA_Earth.addChild(ORB_Moon);

	        //Translation of Moon
	        TransformGroup TRA_Moon = createTranslatingTransformGroup(0.5, 0,0);
	        ORB_Moon.addChild(TRA_Moon);

	        //Rotation speed of Moon
	        int rotationTimeMoon = 500;
	        Sphere moon = new Sphere(0.1f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, 100);

	        Appearance moonAp = moon();
	        moon.setAppearance(moonAp);

	        TransformGroup ROT_Moon = createRotationTransformGroup(rotationTimeMoon, true);
	        ROT_Moon.addChild(moon);
	        TRA_Moon.addChild(ROT_Moon);

	        // Mars \\
	        //Mars orbit speed
	        TransformGroup ORB_Mars = createRotationTransformGroup(11280, false); //188% of base
	        spin.addChild(ORB_Mars);

	        //Translation of Mars
	        TransformGroup TRA_Mars = createTranslatingTransformGroup(4,0,0);
	        ORB_Mars.addChild(TRA_Mars);

	        //Rotation speed of Mars
	        int rotationTimeMars = 1500;
	        Sphere mars = new Sphere(0.2f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, 100);

	        Appearance marsAp = mars();
	        mars.setAppearance(marsAp);

	        TransformGroup ROT_Mars = createRotationTransformGroup(rotationTimeMars, false);
	        ROT_Mars.addChild(mars);
	        TRA_Mars.addChild(ROT_Mars);

	        // Jupiter \\
	        //Jupiter orbit speed
	        TransformGroup ORB_Jupiter = createRotationTransformGroup(71220, false); //1187% of base
	        spin.addChild(ORB_Jupiter);

	        //Translation of Jupiter
	        TransformGroup TRA_Jupiter = createTranslatingTransformGroup(5,0,0);
	        ORB_Jupiter.addChild(TRA_Jupiter);

	        //Rotation speed of Jupiter
	        int rotationTimeJupiter = 1500;
	        Sphere jupiter = new Sphere(0.55f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, 100);

	        Appearance jupiterAp = jupiter();
	        jupiter.setAppearance(jupiterAp);

	        TransformGroup ROT_Jupiter = createRotationTransformGroup(rotationTimeJupiter, false);
	        ROT_Jupiter.addChild(jupiter);
	        TRA_Jupiter.addChild(ROT_Jupiter);

	        // Saturn \\
	        //Saturn orbit speed
	        TransformGroup ORB_Saturn = createRotationTransformGroup(176820, false); //2947% of base (176820) | 80% (141456)
	        spin.addChild(ORB_Saturn);

	        //Translation of Saturn
	        TransformGroup TRA_Saturn = createTranslatingTransformGroup(6,0,0);
	        ORB_Saturn.addChild(TRA_Saturn);

	        //Rotation speed of Saturn
	        int rotationTimeSaturn = 1500;
	        Sphere saturn = new Sphere(0.5f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, 100);

	        Appearance saturnAp = saturn();
	        saturn.setAppearance(saturnAp);

	        TransformGroup ROT_Saturn = createRotationTransformGroup(rotationTimeSaturn, false);
	        ROT_Saturn.addChild(saturn);
	        TRA_Saturn.addChild(ROT_Saturn);

	        // Uranus \\
	        //Uranus orbit speed
	        TransformGroup ORB_Uranus = createRotationTransformGroup(504419, false); //8407% of base (504419) | 80% (403535) | 60% (302651) | 30% (151325)
	        spin.addChild(ORB_Uranus);

	        //Translation of Uranus
	        TransformGroup TRA_Uranus = createTranslatingTransformGroup(7,0,0);
	        ORB_Uranus.addChild(TRA_Uranus);

	        //Rotation speed of Uranus
	        int rotationTimeUranus = 1500;
	        Sphere uranus = new Sphere(0.4f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, 100);

	        Appearance uranusAp = uranus();
	        uranus.setAppearance(uranusAp);

	        TransformGroup ROT_Uranus = createRotationTransformGroup(rotationTimeUranus, false);
	        ROT_Uranus.addChild(uranus);
	        TRA_Uranus.addChild(ROT_Uranus);

	        // Neptune \\
	        //Neptune orbit speed
	        TransformGroup ORB_Neptune = createRotationTransformGroup(989400, false); //16490% of base (989400) | 80% (791520) | 60% (593640) | 30% (296820)
	        spin.addChild(ORB_Neptune);

	        //Translation of Neptune
	        TransformGroup TRA_Neptune = createTranslatingTransformGroup(8,0,0);
	        ORB_Neptune.addChild(TRA_Neptune);

	        //Rotation speed of Neptune
	        int rotationTimeNeptune = 1500;
	        Sphere neptune = new Sphere(0.4f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, 100);

	        Appearance neptuneAp = neptune();
	        neptune.setAppearance(neptuneAp);

	        TransformGroup ROT_Neptune = createRotationTransformGroup(rotationTimeNeptune, false);
	        ROT_Neptune.addChild(neptune);
	        TRA_Neptune.addChild(ROT_Neptune);

	        //Drawing Orbit Lines
	        for(int i = 0; i <= 8; i++) {
	            float radius = 1f * i;
	            LineStripArray line = new LineStripArray(81, GeometryArray.COORDINATES, new int[] { 81 });
	            line.setCoordinate(0, new Point3f(radius, 0.0f, 0.0f));

	            Appearance alternateColor = new Appearance();
	            ColoringAttributes coloring = new ColoringAttributes();
	            coloring.setColor(new Color3f(Color.BLACK));
	            alternateColor.setColoringAttributes(coloring);

	            Point3f pt = new Point3f();

	            for(int j = 1; j < 80; j++) {
	                pt.x = (float) (radius * Math.cos(j * Math.PI / 40));
	                pt.z = (float) (radius * Math.sin(j * Math.PI / 40));
	                line.setCoordinate(j, pt);
	            }

	            line.setCoordinate(80, new Point3f(radius, 0.0f, 0.0f));
	            spin.addChild(new Shape3D(line)); //Change to root if doesn't work
	        }

	        return root;

	    }

	    private static TransformGroup createRotationTransformGroup(int rotationTime, boolean forward) {
	        TransformGroup rotationTG = new TransformGroup();
	        rotationTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

	        Alpha rotationAlpha = new Alpha(-1, rotationTime);

	        float angle;
	        if(forward) {
	            angle = (float) (2 * Math.PI);
	        }

	        else {
	            angle = (float) (-2 * Math.PI);
	        }

	        RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, rotationTG, new Transform3D(), 0, angle);
	        rotator.setSchedulingBounds(bounds);
	        rotationTG.addChild(rotator);

	        return rotationTG;
	    }


	    private static TransformGroup createTranslatingTransformGroup(double dx, double dy, double dz) {
	        TransformGroup translationTG = new TransformGroup();
	        Transform3D translationTF = new Transform3D();

	        translationTF.setTranslation(new Vector3d(dx, dy, dz));
	        translationTG.setTransform(translationTF);

	        return translationTG;
	    }

	    public Background renderStars() {
	        BranchGroup starGroup = new BranchGroup();
	        Background starBg = new Background();
	        starBg.setApplicationBounds(bounds);

	        Appearance starAppearance = stars();

	        Sphere starSphere = new Sphere(1.0f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_NORMALS_INWARD | Sphere.GENERATE_TEXTURE_COORDS, 40, starAppearance);

	        starGroup.addChild(starSphere);
	        starBg.setGeometry(starGroup);

	        return starBg;
	    }

	    Appearance sun() {
	        Appearance appear = new Appearance();
	        URL filename = getClass().getClassLoader().getResource("res/sun.png");
	        TextureLoader loader = new TextureLoader(filename, this);
	        ImageComponent2D image = loader.getImage();
	        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
	        texture.setImage(0, image);
	        texture.setEnable(true);
	        texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
	        texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
	        appear.setTexture(texture);
	        return appear;
	    }

	    Appearance mercury() {
	        Appearance appear = new Appearance();
	        URL filename = getClass().getClassLoader().getResource("res/mercury.png");
	        TextureLoader loader = new TextureLoader(filename, this);
	        ImageComponent2D image = loader.getImage();
	        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
	        texture.setImage(0, image);
	        texture.setEnable(true);
	        texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
	        texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
	        appear.setTexture(texture);
	        return appear;
	    }

	    Appearance venus() {
	        Appearance appear = new Appearance();
	        URL filename = getClass().getClassLoader().getResource("res/venus.png");
	        TextureLoader loader = new TextureLoader(filename, this);
	        ImageComponent2D image = loader.getImage();
	        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
	        texture.setImage(0, image);
	        texture.setEnable(true);
	        texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
	        texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
	        appear.setTexture(texture);
	        return appear;
	    }

	    Appearance earth() {
	        Appearance appear = new Appearance();
	        URL filename = getClass().getClassLoader().getResource("res/earth.png");
	        TextureLoader loader = new TextureLoader(filename, this);
	        ImageComponent2D image = loader.getImage();
	        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
	        texture.setImage(0, image);
	        texture.setEnable(true);
	        texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
	        texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
	        appear.setTexture(texture);
	        return appear;
	    }

	    Appearance moon() {
	        Appearance appear = new Appearance();
	        URL filename = getClass().getClassLoader().getResource("res/moon.png");
	        TextureLoader loader = new TextureLoader(filename, this);
	        ImageComponent2D image = loader.getImage();
	        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
	        texture.setImage(0, image);
	        texture.setEnable(true);
	        texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
	        texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
	        appear.setTexture(texture);
	        return appear;
	    }
	    Appearance mars() {
	        Appearance appear = new Appearance();
	        URL filename = getClass().getClassLoader().getResource("res/mars.png");
	        TextureLoader loader = new TextureLoader(filename, this);
	        ImageComponent2D image = loader.getImage();
	        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
	        texture.setImage(0, image);
	        texture.setEnable(true);
	        texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
	        texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
	        appear.setTexture(texture);
	        return appear;
	    }

	    Appearance jupiter() {
	        Appearance appear = new Appearance();
	        URL filename = getClass().getClassLoader().getResource("res/jupiter.png");
	        TextureLoader loader = new TextureLoader(filename, this);
	        ImageComponent2D image = loader.getImage();
	        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
	        texture.setImage(0, image);
	        texture.setEnable(true);
	        texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
	        texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
	        appear.setTexture(texture);
	        return appear;
	    }

	    Appearance saturn() {
	        Appearance appear = new Appearance();
	        URL filename = getClass().getClassLoader().getResource("res/saturn.png");
	        TextureLoader loader = new TextureLoader(filename, this);
	        ImageComponent2D image = loader.getImage();
	        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
	        texture.setImage(0, image);
	        texture.setEnable(true);
	        texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
	        texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
	        appear.setTexture(texture);
	        return appear;
	    }

	    Appearance uranus() {
	        Appearance appear = new Appearance();
	        URL filename = getClass().getClassLoader().getResource("res/uranus.png");
	        TextureLoader loader = new TextureLoader(filename, this);
	        ImageComponent2D image = loader.getImage();
	        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
	        texture.setImage(0, image);
	        texture.setEnable(true);
	        texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
	        texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
	        appear.setTexture(texture);
	        return appear;
	    }

	    Appearance neptune() {
	        Appearance appear = new Appearance();
	        URL filename = getClass().getClassLoader().getResource("res/neptune.png");
	        TextureLoader loader = new TextureLoader(filename, this);
	        ImageComponent2D image = loader.getImage();
	        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
	        texture.setImage(0, image);
	        texture.setEnable(true);
	        texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
	        texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
	        appear.setTexture(texture);
	        return appear;
	    }

	    Appearance stars() {
	        Appearance appear = new Appearance();
	        URL filename = getClass().getClassLoader().getResource("res/stars.png");
	        TextureLoader loader = new TextureLoader(filename, this);
	        ImageComponent2D image = loader.getImage();
	        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
	        texture.setImage(0, image);
	        texture.setEnable(true);
	        texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
	        texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
	        appear.setTexture(texture);
	        return appear;
	    }
}
