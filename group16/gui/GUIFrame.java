package group16.gui;

import group16.gamemodes.RandomOrder;
import group16.gamemodes.ToTheBitterEnd;
import group16.gamemodes.UpperBound;
import group16.graph.GraphGenerator;
import group16.gui.GamemodeHints.ExactHint;
import group16.gui.GamemodeHints.RandomOrderHint;
import group16.gui.GamemodeHints.ToTheBitterEndHint;
import group16.gui.GamemodeHints.UpperBoundHint;
import group16.gui.GraphRepresentation.DrawableGraph;

import javax.swing.*;

import static group16.graph.GraphReader.readGraph;
import static group16.gui.GUI.*;
import static group16.gui.GUI.guiFrame;
import static group16.util.GUIUtil.createFrame;

public class GUIFrame extends JFrame {

    private JPanel mainPanel;
    private JPanel mainMenuPanel;
    private JButton quitButton;

    private JButton chooseGameModeButton;
    public JPanel chooseGameModePanel;
    private JButton chooseGameModeBackButton;

    private JButton toTheBitterEndButton;
    public JPanel toTheBitterEndPanel;
    public JLabel toTheBitterEndTimeLabel;
    private JLabel toTheBitterEndColorCountLabel;
    private JButton toTheBitterEndHintButton;
    private JButton toTheBitterEndBackButton;
    public JPanel toTheBitterEndGraphPanel;
    private JButton toTheBitterEndResetGraphButton;

    private JButton bestUpperboundButton;
    public JPanel bestUpperboundPanel;
    public JLabel bestUpperboundTimeLabel;
    private JLabel bestUpperboundColorCountLabel;
    private JButton bestUpperboundBackButton;
    private JButton bestUpperboundHintButton;
    public JPanel bestUpperboundGraphPanel;
    private JButton bestUpperboundResetGraphButton;

    private JButton randomOrderButton;
    public JPanel randomOrderPanel;
    private JLabel randomOrderColorCountLabel;
    private JButton randomOrderBackButton;
    private JButton randomOrderHintButton;
    public JPanel randomOrderGraphPanel;

    private JButton chooseGraphButton;
    private JPanel chooseGraphPanel;
    private JButton chooseGraphBackButton;

    private JButton customGraphButton;
    private JPanel customGraphPanel;
    private JTextField verticesTextField;
    private JButton confirmButton;
    private JTextField edgesTextField;
    private JButton customGraphBackButton;

    private JButton chooseFromFileButton;
    private JPanel graphFromFilePanel;
    private JButton readGraphButton;
    private JButton graphFromFileBackButton;
    private JLabel graphNameLabel;

    private boolean isTimerOn;
    private Thread thread;
    private int time;

    public GUIFrame() {
        add(mainPanel);
        mainMenu();
        createFrame(this);
    }

    private void mainMenu() {
        chooseGameMode();
        chooseGraph();
        quitButton.addActionListener(e -> dispose());
    }

    private void chooseGameMode() {
        changePanel(chooseGameModeButton, mainMenuPanel, chooseGameModePanel);
        toTheBitterEnd();
        bestUpperbound();
        randomOrder();
        changePanel(chooseGameModeBackButton, chooseGameModePanel, mainMenuPanel);
    }

    private void toTheBitterEnd() {
        changePanel(toTheBitterEndButton, chooseGameModePanel, toTheBitterEndPanel);
        isTimerOn = true;
        toTheBitterEndHintButton.addActionListener(new ToTheBitterEndHint());
        toTheBitterEndButton.addActionListener(e -> {
            time = 0;
            startGameMode(toTheBitterEndTimeLabel, toTheBitterEndGraphPanel, toTheBitterEndColorCountLabel, "ToTheBitterEnd");
        });
        toTheBitterEndResetGraphButton.addActionListener(e -> resetGraph(toTheBitterEndGraphPanel, toTheBitterEndColorCountLabel, "ToTheBitterEnd"));
        toTheBitterEndBackButton.addActionListener(e -> stopGameMode(toTheBitterEndPanel, toTheBitterEndGraphPanel));
    }

    private void bestUpperbound() {
        changePanel(bestUpperboundButton, chooseGameModePanel, bestUpperboundPanel);
        bestUpperboundHintButton.addActionListener(new UpperBoundHint());
        bestUpperboundButton.addActionListener(e -> {
            time = 300;
            startGameMode(bestUpperboundTimeLabel, bestUpperboundGraphPanel, bestUpperboundColorCountLabel, "BestUpperbound");
        });
        bestUpperboundResetGraphButton.addActionListener(e -> resetGraph(bestUpperboundGraphPanel, bestUpperboundColorCountLabel, "BestUpperbound"));
        bestUpperboundBackButton.addActionListener(e -> stopGameMode(bestUpperboundPanel, bestUpperboundGraphPanel));
    }

    private void randomOrder() {
        changePanel(randomOrderButton, chooseGameModePanel, randomOrderPanel);
        randomOrderHintButton.addActionListener(new RandomOrderHint());
        randomOrderButton.addActionListener(e -> addGraph(randomOrderGraphPanel, randomOrderColorCountLabel, "RandomOrder"));
        randomOrderBackButton.addActionListener(e -> stopGameMode(guiFrame.randomOrderPanel, guiFrame.randomOrderGraphPanel));
    }

    private void startGameMode(JLabel timeLabel, JPanel graphPanel, JLabel countLabel, String gameMode) {
        isTimerOn = true;
        setTimer(timeLabel);
        addGraph(graphPanel, countLabel, gameMode);
        thread = new Thread(new Timer());
        thread.start();
    }

    public void stopGameMode(JPanel gamePanel, JPanel graphPanel) {
        isTimerOn = false;
        if (thread != null) {
            thread.interrupt();
        }
        gamePanel.setVisible(false);
        chooseGameModePanel.setVisible(true);
        graphPanel.removeAll();
    }

    private void resetGraph(JPanel graphPanel, JLabel countLabel, String gameMode) {
        graphPanel.removeAll();
        addGraph(graphPanel, countLabel, gameMode);
    }

    private void addGraph(JPanel graphPanel, JLabel countLabel, String gameMode) {
        countLabel.setText("0");
        DrawableGraph drawableGraphCopy;
        if (drawableGraph != null) {
            drawableGraphCopy = drawableGraph.copyGraph();
            switch (gameMode) {
                case "ToTheBitterEnd" -> {
                    ToTheBitterEnd graphDrawer = new ToTheBitterEnd(drawableGraphCopy, countLabel);
                    graphPanel.add(graphDrawer);
                }
                case "BestUpperbound" -> {
                    UpperBound graphDrawer = new UpperBound(drawableGraphCopy, countLabel);
                    graphPanel.add(graphDrawer);
                }
                case "RandomOrder" -> {
                    RandomOrder graphDrawer = new RandomOrder(drawableGraphCopy, countLabel);
                    graphPanel.add(graphDrawer);
                }
            }
        }
    }

    private void chooseGraph() {
        changePanel(chooseGraphButton, mainMenuPanel, chooseGraphPanel);
        chooseFromFile();
        customGraph();
        changePanel(chooseGraphBackButton, chooseGraphPanel, mainMenuPanel);
        chooseGraphBackButton.addActionListener(e -> graphNameLabel.setVisible(false));
    }

    private void customGraph() {
        changePanel(customGraphButton, chooseGraphPanel, customGraphPanel);
        confirmButton.addActionListener(e -> {
            int verticesCount = Integer.parseInt(verticesTextField.getText());
            int edgesCount = Integer.parseInt(edgesTextField.getText());
            if (edgesCount > verticesCount * ( verticesCount - 1 ) / 2) {
                JOptionPane.showMessageDialog(null, "A graph with "  + verticesCount + " vertices can't have that may edges.");
            } else {
                graph = GraphGenerator.generateGraph(verticesCount, edgesCount);
                drawableGraph = new DrawableGraph(toTheBitterEndGraphPanel.getHeight(), toTheBitterEndGraphPanel.getWidth());
                graphNameLabel.setText("Custom graph: " + verticesCount + " vertices, " + edgesCount + " edges.");
                graphNameLabel.setVisible(true);
                customGraphPanel.setVisible(false);
                chooseGraphPanel.setVisible(true);
            }
        });
        changePanel(customGraphBackButton, customGraphPanel, chooseGraphPanel);
    }

    private void chooseFromFile() {
        changePanel(chooseFromFileButton, chooseGraphPanel, graphFromFilePanel);
        readGraphButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(this);
            if (fileChooser.getSelectedFile() != null) {
                String inputFile = fileChooser.getSelectedFile().getAbsolutePath();
                graph = readGraph(inputFile);
                drawableGraph = new DrawableGraph(toTheBitterEndGraphPanel.getHeight(), toTheBitterEndGraphPanel.getWidth());
                graphNameLabel.setText("Graph name: " + fileChooser.getSelectedFile().getName());
                graphNameLabel.setVisible(true);
                graphFromFilePanel.setVisible(false);
                chooseGraphPanel.setVisible(true);
            }
        });
        changePanel(graphFromFileBackButton, graphFromFilePanel, chooseGraphPanel);
    }

    private void changePanel(JButton button, JPanel panel, JPanel desiredPanel) {
        button.addActionListener(e -> {
            panel.setVisible(false);
            desiredPanel.setVisible(true);
        });
    }

    private class Timer implements Runnable {

        @Override
        public void run() {
            if (time == 0) {
                stopwatch();
            } else {
                countDown();
            }
        }

        private void stopwatch() {
            while (isTimerOn) {
                try {
                    time++;
                    Thread.sleep(1000);
                    setTimer(toTheBitterEndTimeLabel);
                } catch (InterruptedException e) {
                    System.out.println("Timer was stopped.");
                }
            }
        }

        private void countDown() {
            while (time-- > 0 && isTimerOn) {
                try {
                    Thread.sleep(1000);
                    setTimer(bestUpperboundTimeLabel);
                } catch (InterruptedException e) {
                    System.out.println("Timer was stopped.");
                }
            }
            if (time == 0) {
                stopGameMode(bestUpperboundPanel, bestUpperboundGraphPanel);
                JOptionPane.showMessageDialog(null, "You failed(Reason: time ran out)\n Better luck on your next try");
            }
        }
    }

    public void setTimer(JLabel timeLabel) {
        String minute;
        String seconds;
        minute = "" + time / 60;
        seconds = "" + time % 60;
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        if (seconds.length() == 1) {
            seconds = "0" + seconds;
        }
        timeLabel.setText(minute + ":" + seconds);
    }
}
