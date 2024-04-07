file:///C:/Users/celes/A_Sector_To_Wreck/Jaccal__uni-sys-integr/app/src/main/java/javacalendar/Main.java
### java.util.NoSuchElementException: next on empty iterator

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.1
Classpath:
<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala3-library_3\3.3.1\scala3-library_3-3.3.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.10\scala-library-2.13.10.jar [exists ]
Options:



action parameters:
offset: 237
uri: file:///C:/Users/celes/A_Sector_To_Wreck/Jaccal__uni-sys-integr/app/src/main/java/javacalendar/Main.java
text:
```scala
package javacalendar;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javacalendar.mainui.*; 
import scala.j@@
import javacalendar.TestThing;

public class Main implements KeyListener {
    private final JFrame mainWindow;

    public static final int WINDOW_WIDTH = 1450;
    public static final int WINDOW_HEIGHT = 800; // Originally 850

    public Main() {
      // MySQL JDBC driver connection (does that work? we'll see!).
      try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        mainWindow = new JFrame("JavaCalendar");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainWindow.setResizable(true);
        mainWindow.setLayout(new BorderLayout());  
        // A "nooby", albeit functional way to center the window, I suppose.
        mainWindow.setLocationRelativeTo(null);

        mainWindow.addKeyListener(this);

        addMainComponents();
    }

    private void addMainComponents() {
        mainWindow.add(new WeekViewCalendar(), BorderLayout.CENTER);
        mainWindow.add(new LeftBarPanel(), BorderLayout.WEST);
        mainWindow.setJMenuBar(new MenuBar());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Invoking a runnable asynchronously (the lambda is the "Runnable" class; or the run() method).
            Main window = new Main();
            window.mainWindow.setVisible(true);
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    /*
     * Ctrl + W closes the main window.
     */
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_W) && (e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0) {
            mainWindow.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}

```



#### Error stacktrace:

```
scala.collection.Iterator$$anon$19.next(Iterator.scala:973)
	scala.collection.Iterator$$anon$19.next(Iterator.scala:971)
	scala.collection.mutable.MutationTracker$CheckedIterator.next(MutationTracker.scala:76)
	scala.collection.IterableOps.head(Iterable.scala:222)
	scala.collection.IterableOps.head$(Iterable.scala:222)
	scala.collection.AbstractIterable.head(Iterable.scala:933)
	dotty.tools.dotc.interactive.InteractiveDriver.run(InteractiveDriver.scala:168)
	scala.meta.internal.pc.MetalsDriver.run(MetalsDriver.scala:45)
	scala.meta.internal.pc.completions.CompletionProvider.completions(CompletionProvider.scala:46)
	scala.meta.internal.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:146)
```
#### Short summary: 

java.util.NoSuchElementException: next on empty iterator