import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamResolution;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebCam_MotionDetector {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		


		Webcam webcAm = Webcam.getWebcams().get(1);
		WebcamMotionDetector detector = new WebcamMotionDetector(Webcam.getWebcams().get(1));
		detector.setInterval(100); // one check per 100 ms
		
      
		
        
			detector.start();
			
		    if (detector.isMotion()) {
		    	
		        System.out.println("Motion detected!!!");		        
		        detector.stop();
		        
		        File file = new File("output.mp4");

		        IMediaWriter writer = ToolFactory.makeWriter(file.getName());
		        Dimension size = WebcamResolution.VGA.getSize();

		        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);
		        writer.addAudioStream(1,0,2,44100);
		        webcAm.setViewSize(size);
		        		        
				webcAm.open(true);
	            
	            long start = System.currentTimeMillis();
				for (int i = 0; i < 100; i++) {

					System.out.println("Capture frame " + i);

                    BufferedImage image = ConverterFactory.convertToType(webcAm.getImage(), BufferedImage.TYPE_3BYTE_BGR);
                    IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);

                    IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 1000);
                    frame.setKeyFrame(i == 0);
                    frame.setQuality(0);

                    writer.encodeVideo(0, frame);

					try {
						Thread.sleep((long) 0.0000000000001);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
				}

				writer.close();

				System.out.println("Video recorded in file: " + file.getAbsolutePath());

			    webcAm.close();
			// initialize the transport
      httpTransport = GoogleNetHttpTransport.newTrustedTransport();

      // initialize the data store factory
      dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);

      // authorization
      Credential credential = authorize();

      // set up global Drive instance
      client = new Drive.Builder(httpTransport, JSON_FACTORY, credential)
          .setApplicationName(APPLICATION_NAME).build();

      System.out.println("Google drive authenticated");
      //System.out.println(client.files());


     
    //Insert a video file  
    File body = new File();
    body.setTitle("My motion video");
    body.setDescription("BLA BLA");
    body.setMimeType("video/mp4");
    
    java.io.File fileContent = new java.io.File("output.mp4");
    FileContent mediaContent = new FileContent("video/mp4", fileContent);

    File file = client.files().insert(body, mediaContent).execute();
    System.out.println("File ID: " + file.getId());
System.out.println("Successfully uploaded");


    } catch (IOException e) {
      System.err.println(e.getMessage());
    } catch (Throwable t) {
      t.printStackTrace();
    }
    System.exit(1);

			    
		    
    }



}
}
