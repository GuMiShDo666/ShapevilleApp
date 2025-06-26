import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.text.DecimalFormat;
import java.awt.geom.Path2D;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
/**
 * ShapevilleApp - A Geometry Learning Application
 * This application provides an interactive learning environment for geometry concepts
 * including shape recognition, angle identification, area calculations, and more.
 *
 * @author Group 46
 * @version 1.0
 * @since 2025
 */
public class ShapevilleApp {
    // UI Color constants for cartoon-style interface
    private static final Color CARTOON_BACKGROUND = new Color(255, 255, 240);
    private static final Color CARTOON_BUTTON = new Color(255, 182, 193);
    private static final Color CARTOON_BUTTON_HOVER = new Color(255, 150, 150);
    private static final Color CARTOON_TEXT = new Color(70, 130, 180);
    private static final Color CARTOON_BORDER = new Color(255, 160, 122);

    // Font constants for UI elements
    private static final Font CARTOON_TITLE_FONT = new Font("Comic Sans MS", Font.BOLD, 24);
    private static final Font CARTOON_TEXT_FONT = new Font("Comic Sans MS", Font.PLAIN, 16);
    private static final Font CARTOON_BUTTON_FONT = new Font("Comic Sans MS", Font.BOLD, 16);

    // Main application components
    private JFrame mainFrame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private int score = 0;
    private int progress = 0;
    private Random random = new Random();
    private JLabel scoreLabel;
    private JProgressBar progressBar;
    private Set<String> completedTasks = new HashSet<>();
    private JLabel timerLabel;

    /**
     * Main entry point of the application
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        new ShapevilleApp().createAndShowGUI();
    }

    /**
     * Creates and initializes the main application window and UI components
     */
    private void createAndShowGUI() {
        // Initialize main window
        mainFrame = new JFrame("Shapeville - Geometry Learning App");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.getContentPane().setBackground(CARTOON_BACKGROUND);

        // Add custom border
        mainFrame.getRootPane().setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARTOON_BORDER, 3),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Initialize main panel with card layout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(CARTOON_BACKGROUND);

        // Add all task panels
        mainPanel.add(createHomePanel(), "home");
        mainPanel.add(createKS1Task1Panel(), "ks1_task1");
        mainPanel.add(createKS1Task2Panel(), "ks1_task2");
        mainPanel.add(createShapeAreaCalculationPanel(), "ks2_task3");
        mainPanel.add(createCircleCalculationPanel(), "ks2_task4");
        mainPanel.add(new BonusTaskCompositePanel(), "bonus_task_composite");
        mainPanel.add(new BonusTaskSectorPanel(), "bonus_task_sector");

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    /**
     * Marks a task as completed and updates progress
     * @param taskId The ID of the completed task
     */
    private void completeTask(String taskId) {
        if (!completedTasks.contains(taskId)) {
            completedTasks.add(taskId);
            progress = (int) (((double) completedTasks.size() / 6) * 100);
            if (scoreLabel != null) scoreLabel.setText("Current Score: " + score);
            if (progressBar != null) progressBar.setValue(progress);
        }
    }

    /**
     * Determines the correct shape name based on description
     * @param description The shape description
     * @return The corresponding shape name
     */
    private String getCorrectShapeForDescription(String description) {
        if (description.contains("4 equal sides")) return "Square";
        if (description.contains("round")) return "Circle";
        if (description.contains("3 sides")) return "Triangle";
        if (description.contains("4 sides") && description.contains("opposite sides")) return "Rectangle";
        return "Square";
    }

    /**
     * Determines the angle type based on degree value
     * @param degree The angle in degrees
     * @return The type of angle (Acute/Right/Obtuse/Reflex)
     */
    private String getAngleType(int degree) {
        if (degree < 90) return "Acute";
        if (degree == 90) return "Right";
        if (degree < 180) return "Obtuse";
        return "Reflex";
    }



    /**
     * Creates the home interface panel
     * @return Home interface panel
     */
    /**
     * Creates the home interface panel with score display, progress bar, and task selection buttons
     * @return The configured home panel
     */
    private JPanel createHomePanel() {
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(CARTOON_BACKGROUND);

        // Create header with application title
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(CARTOON_BACKGROUND);
        JLabel titleLabel = new JLabel("Shapeville - Geometry Learning App");
        titleLabel.setFont(CARTOON_TITLE_FONT);
        titleLabel.setForeground(CARTOON_TEXT);
        headerPanel.add(titleLabel);
        homePanel.add(headerPanel, BorderLayout.NORTH);

        // Create content panel with score, progress, and task buttons
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(CARTOON_BACKGROUND);

        // Add score display
        scoreLabel = new JLabel("Current Score: " + score);
        scoreLabel.setFont(CARTOON_TEXT_FONT);
        scoreLabel.setForeground(CARTOON_TEXT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(scoreLabel);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Add progress section
        JLabel progressLabel = new JLabel("Learning Progress");
        progressLabel.setFont(CARTOON_TEXT_FONT);
        progressLabel.setForeground(CARTOON_TEXT);
        progressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(progressLabel);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(progress);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(400, 30));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressBar.setForeground(CARTOON_BUTTON);
        progressBar.setBackground(Color.WHITE);
        contentPanel.add(progressBar);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Add task level section
        JLabel levelLabel = new JLabel("Available Levels");
        levelLabel.setFont(CARTOON_TEXT_FONT);
        levelLabel.setForeground(CARTOON_TEXT);
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(levelLabel);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Create grid of task level buttons
        JPanel levelButtonPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        levelButtonPanel.setMaximumSize(new Dimension(600, 200));
        levelButtonPanel.setBackground(CARTOON_BACKGROUND);
        levelButtonPanel.add(createLevelButton("KS1 - Shape Recognition", "ks1_task1"));
        levelButtonPanel.add(createLevelButton("KS2 - Area Calculation", "ks2_task3"));
        levelButtonPanel.add(createLevelButton("KS1 - Angle Type Identification", "ks1_task2"));
        levelButtonPanel.add(createLevelButton("KS2 - Circle Calculation", "ks2_task4"));
        levelButtonPanel.add(createLevelButton("Bonus Task - Composite Figures", "bonus_task_composite"));
        levelButtonPanel.add(createLevelButton("Bonus Task - Sector Calculation", "bonus_task_sector"));
        contentPanel.add(levelButtonPanel);

        homePanel.add(contentPanel, BorderLayout.CENTER);

        // Add end session button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(CARTOON_BACKGROUND);
        JButton endSessionButton = createCartoonButton("End Session");
        endSessionButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(mainFrame,
                    "Are you sure you want to end the current session?",
                    "Confirm End", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(mainFrame,
                        "Final Score: " + score,
                        "Session Ended", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        });
        buttonPanel.add(endSessionButton);
        homePanel.add(buttonPanel, BorderLayout.SOUTH);

        return homePanel;
    }

    /**
     * Creates a cartoon-styled button with hover effects
     * @param text The button text
     * @return The configured button
     */
    private JButton createCartoonButton(String text) {
        JButton button = new JButton(text);
        button.setFont(CARTOON_BUTTON_FONT);
        button.setForeground(CARTOON_TEXT);
        button.setBackground(CARTOON_BUTTON);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARTOON_BORDER, 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(CARTOON_BUTTON_HOVER);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(CARTOON_BUTTON);
            }
        });

        return button;
    }

    /**
     * Creates a level selection button that switches to the specified panel
     * @param text The button text
     * @param panelName The name of the panel to show when clicked
     * @return The configured button
     */
    private JButton createLevelButton(String text, String panelName) {
        JButton button = createCartoonButton(text);
        button.addActionListener(e -> cardLayout.show(mainPanel, panelName));
        return button;
    }

    /**
     * Manages the collection of shapes available in the application
     * Handles initialization and random retrieval of 2D and 3D shapes
     */
    class ShapeList {
        private final List<Shape> shapeList = new ArrayList<>();

        /**
         * Initializes the shape list with predefined 2D and 3D shapes
         */
        public ShapeList() {
            // Initialize 2D shapes
            shapeList.add(new Shape("circle", "A round shape with no corners", 2));
            shapeList.add(new Shape("rectangle", "A shape with 4 sides and 4 right angles, opposite sides are equal", 2));
            shapeList.add(new Shape("triangle", "A shape with 3 sides and 3 angles", 2));
            shapeList.add(new Shape("oval", "An elongated round shape", 2));
            shapeList.add(new Shape("octagon", "A shape with 8 sides", 2));
            shapeList.add(new Shape("square", "A shape with 4 equal sides and 4 right angles", 2));
            shapeList.add(new Shape("heptagon", "A shape with 7 sides", 2));
            shapeList.add(new Shape("rhombus", "A shape with 4 equal sides, opposite angles equal", 2));
            shapeList.add(new Shape("pentagon", "A shape with 5 sides", 2));
            shapeList.add(new Shape("hexagon", "A shape with 6 sides", 2));
            shapeList.add(new Shape("kite", "A shape with two distinct pairs of adjacent sides equal", 2));

            // Initialize 3D shapes
            shapeList.add(new Shape("cube", "A 3D shape with 6 equal square faces", 3));
            shapeList.add(new Shape("cuboid", "A 3D shape with 6 rectangular faces", 3));
            shapeList.add(new Shape("cylinder", "A 3D shape with two circular bases and a curved surface", 3));
            shapeList.add(new Shape("sphere", "A perfectly round 3D shape like a ball", 3));
            shapeList.add(new Shape("cone", "A 3D shape with a circular base and a pointed top", 3));
            shapeList.add(new Shape("triangular prism", "A 3D shape with triangular ends and rectangular faces", 3));
            shapeList.add(new Shape("square - based pyramid", "A 3D shape with a square base and triangular faces meeting at a point", 3));
            shapeList.add(new Shape("tetrahedron", "A 3D shape with 4 triangular faces", 3));
        }

        /**
         * Retrieves a random shape from the list
         * @return A randomly selected shape
         */
        public Shape getRandomShape() {
            return shapeList.get(new Random().nextInt(shapeList.size()));
        }

        /**
         * Represents a geometric shape with its properties
         */
        class Shape {
            private final String name;
            private final String description;
            private final int dimension;

            /**
             * Creates a new shape with specified properties
             * @param name The name of the shape
             * @param description A description of the shape
             * @param dimension The dimension (2 for 2D, 3 for 3D)
             */
            public Shape(String name, String description, int dimension) {
                this.name = name;
                this.description = description;
                this.dimension = dimension;
            }

            public String getName() {
                return name;
            }

            public String getDescription() {
                return description;
            }

            public int getDimension() {
                return dimension;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                Shape shape = (Shape) obj;
                return name.equals(shape.name);
            }

            @Override
            public int hashCode() {
                return name.hashCode();
            }
        }
    }

    /**
     * Manages the shape recognition task flow and state
     * Handles user interactions, scoring, and progress tracking
     */
    class ShapeTask {
        private final ShapeList shapeList;
        private List<ShapeList.Shape> shapesToIdentify;
        private int currentIndex;
        private ShapeList.Shape currentShape;
        private int attemptsLeft;
        private static final int MAX_ATTEMPTS = 3;
        private static final int TOTAL_SHAPES = 4;
        private int targetDimension;

        /**
         * Initializes a new shape recognition task
         */
        public ShapeTask() {
            shapeList = new ShapeList();
            shapesToIdentify = new ArrayList<>();
            currentIndex = 0;
            attemptsLeft = MAX_ATTEMPTS;
        }

        /**
         * Starts a new task with shapes of the specified dimension
         * @param dimension The target dimension (2 for 2D, 3 for 3D)
         */
        public void startNewTask(int dimension) {
            targetDimension = dimension;
            shapesToIdentify.clear();
            List<ShapeList.Shape> allShapes = new ArrayList<>();

            // Filter shapes by dimension
            for (int i = 0; i < 100; i++) {
                ShapeList.Shape shape = shapeList.getRandomShape();
                if (shape.getDimension() == targetDimension && !allShapes.contains(shape)) {
                    allShapes.add(shape);
                }
                if (allShapes.size() >= TOTAL_SHAPES) break;
            }

            // Randomly select shapes for the task
            Collections.shuffle(allShapes);
            shapesToIdentify = allShapes.subList(0, Math.min(TOTAL_SHAPES, allShapes.size()));
            currentIndex = 0;
            currentShape = shapesToIdentify.get(currentIndex);
            attemptsLeft = MAX_ATTEMPTS;
        }

        /**
         * Advances to the next shape in the task
         * @return true if there are more shapes, false if task is complete
         */
        public boolean nextTask() {
            currentIndex++;
            if (currentIndex < shapesToIdentify.size()) {
                currentShape = shapesToIdentify.get(currentIndex);
                attemptsLeft = MAX_ATTEMPTS;
                return true;
            }
            return false;
        }

        public String getCurrentDescription() {
            return currentShape != null ? currentShape.getDescription() : "";
        }

        public String getCurrentName() {
            return currentShape != null ? currentShape.getName() : "";
        }

        public ShapeList.Shape getCurrentShape() {
            return currentShape;
        }

        public int getAttemptsLeft() {
            return attemptsLeft;
        }

        public void decrementAttempts() {
            attemptsLeft--;
        }

        /**
         * Checks if the user's answer matches the current shape
         * @param userInput The user's answer
         * @return true if correct, false otherwise
         */
        public boolean checkAnswer(String userInput) {
            if (currentShape == null) return false;
            boolean correct = userInput.trim().toLowerCase().equals(currentShape.getName().toLowerCase());
            if (!correct) {
                attemptsLeft--;
            }
            return correct;
        }

        public boolean isTaskCompleted() {
            return currentIndex >= TOTAL_SHAPES - 1;
        }

        public int getCurrentIndex() {
            return currentIndex;
        }
    }



    /**
     * Creates the KS1 Task 1 panel for shape recognition
     * This panel allows users to practice identifying 2D and 3D shapes
     * @return The configured shape recognition task panel
     */
    private JPanel createKS1Task1Panel() {
        JPanel mainTaskPanel = new JPanel(new CardLayout());
        CardLayout taskCardLayout = (CardLayout) mainTaskPanel.getLayout();

        // Create shape type selection panel
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add title and selection buttons
        JLabel titleLabel = new JLabel("Select Shape Type to Identify");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btn2D = new JButton("2D Shapes");
        btn2D.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn2D.setMaximumSize(new Dimension(200, 50));

        JButton btn3D = new JButton("3D Shapes");
        btn3D.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn3D.setMaximumSize(new Dimension(200, 50));

        JButton backButton = new JButton("Back to Main Menu");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(200, 50));

        // Layout selection panel components
        selectionPanel.add(Box.createVerticalGlue());
        selectionPanel.add(titleLabel);
        selectionPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        selectionPanel.add(btn2D);
        selectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        selectionPanel.add(btn3D);
        selectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        selectionPanel.add(backButton);
        selectionPanel.add(Box.createVerticalGlue());

        // Create task panel for shape recognition
        JPanel taskPanel = new JPanel(new BorderLayout());
        ShapeTask shapeTask = new ShapeTask();

        // Create UI components for task interaction
        JLabel progressLabel = new JLabel("Progress: 0/4");
        JLabel attemptsLabel = new JLabel("Attempts left: 3");
        JLabel descriptionLabel = new JLabel();
        JTextField answerField = new JTextField(20);
        JButton submitButton = new JButton("Submit");
        JButton showAnswerButton = new JButton("Show Answer");
        JButton nextButton = new JButton("Next");
        JButton backToSelectionButton = new JButton("Back to Selection");

        // Create drawing panel for shape visualization
        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (shapeTask.getCurrentShape() != null) {
                    String imagePath = shapeTask.getCurrentShape().getDimension() == 2 ?
                            "image2D/" + shapeTask.getCurrentShape().getName() + ".png" :
                            "image3D/" + shapeTask.getCurrentShape().getName() + ".png";
                    try {
                        BufferedImage img = ImageIO.read(new File(imagePath));
                        int x = (getWidth() - img.getWidth()) / 2;
                        int y = (getHeight() - img.getHeight()) / 2;
                        g.drawImage(img, x, y, null);
                    } catch (IOException e) {
                        g.setColor(Color.RED);
                        g.drawString("Image not found: " + imagePath, 10, getHeight() / 2);
                    }
                }
            }
        };
        drawingPanel.setPreferredSize(new Dimension(400, 300));

        // Layout control panel components
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.add(progressLabel);
        labelPanel.add(Box.createHorizontalStrut(20));
        labelPanel.add(attemptsLabel);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter shape name: "));
        inputPanel.add(answerField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(showAnswerButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(backToSelectionButton);

        controlPanel.add(labelPanel);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        controlPanel.add(descriptionLabel);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        controlPanel.add(inputPanel);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        controlPanel.add(buttonPanel);

        taskPanel.add(drawingPanel, BorderLayout.CENTER);
        taskPanel.add(controlPanel, BorderLayout.SOUTH);

        // Add panels to main task panel
        mainTaskPanel.add(selectionPanel, "selection");
        mainTaskPanel.add(taskPanel, "task");

        // Initialize UI state
        showAnswerButton.setEnabled(false);
        nextButton.setEnabled(false);

        // Add action listeners
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "home"));

        btn2D.addActionListener(e -> {
            shapeTask.startNewTask(2);
            updateProgressLabel(progressLabel, shapeTask, 0);
            attemptsLabel.setText("Attempts left: " + shapeTask.getAttemptsLeft());
            descriptionLabel.setText(shapeTask.getCurrentDescription());
            answerField.setText("");
            showAnswerButton.setEnabled(false);
            nextButton.setEnabled(false);
            drawingPanel.repaint();
            taskCardLayout.show(mainTaskPanel, "task");
        });

        btn3D.addActionListener(e -> {
            shapeTask.startNewTask(3);
            updateProgressLabel(progressLabel, shapeTask, 0);
            attemptsLabel.setText("Attempts left: " + shapeTask.getAttemptsLeft());
            descriptionLabel.setText(shapeTask.getCurrentDescription());
            answerField.setText("");
            showAnswerButton.setEnabled(false);
            nextButton.setEnabled(false);
            drawingPanel.repaint();
            taskCardLayout.show(mainTaskPanel, "task");
        });

        submitButton.addActionListener(e -> {
            String answer = answerField.getText().trim().toLowerCase();
            boolean correct = shapeTask.checkAnswer(answer);

            if (correct) {
                int pointsToAdd = 0;
                int attemptsUsed = 3 - shapeTask.getAttemptsLeft();

                // Calculate points based on dimension and attempts
                if (shapeTask.getCurrentShape().getDimension() == 2) {
                    switch (attemptsUsed) {
                        case 0: pointsToAdd = 3; break;
                        case 1: pointsToAdd = 2; break;
                        case 2: pointsToAdd = 1; break;
                    }
                } else {
                    switch (attemptsUsed) {
                        case 0: pointsToAdd = 6; break;
                        case 1: pointsToAdd = 4; break;
                        case 2: pointsToAdd = 2; break;
                    }
                }

                score += pointsToAdd;
                if (scoreLabel != null) scoreLabel.setText("Current Score: " + score);

                JOptionPane.showMessageDialog(mainFrame,
                        "Great job! You earned " + pointsToAdd + " points!",
                        "Correct!",
                        JOptionPane.INFORMATION_MESSAGE);

                showAnswerButton.setEnabled(false);
                nextButton.setEnabled(true);
                submitButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Incorrect! Try again.", "Result", JOptionPane.ERROR_MESSAGE);

                attemptsLabel.setText("Attempts left: " + shapeTask.getAttemptsLeft());

                if (shapeTask.getAttemptsLeft() == 0) {
                    showAnswerButton.setEnabled(true);
                    nextButton.setEnabled(true);
                    submitButton.setEnabled(false);
                }
            }
        });

        showAnswerButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainFrame,
                    "The correct answer is: " + shapeTask.getCurrentName(),
                    "Answer", JOptionPane.INFORMATION_MESSAGE);
        });

        nextButton.addActionListener(e -> {
            if (shapeTask.isTaskCompleted()) {
                completeTask("ks1_task1");
                taskCardLayout.show(mainTaskPanel, "selection");
            } else {
                shapeTask.nextTask();
                updateProgressLabel(progressLabel, shapeTask, shapeTask.getCurrentIndex());
                descriptionLabel.setText(shapeTask.getCurrentDescription());
                answerField.setText("");
                showAnswerButton.setEnabled(false);
                nextButton.setEnabled(false);
                submitButton.setEnabled(true);
                attemptsLabel.setText("Attempts left: " + shapeTask.getAttemptsLeft());
                drawingPanel.repaint();
            }
        });

        backToSelectionButton.addActionListener(e -> {
            taskCardLayout.show(mainTaskPanel, "selection");
        });

        return mainTaskPanel;
    }

    /**
     * Updates the progress label with current task information
     * @param label The label to update
     * @param task The current shape task
     * @param index The current task index
     */
    private void updateProgressLabel(JLabel label, ShapeTask task, int index) {
        label.setText("Shape " + (index + 1) + " of " + ShapeTask.TOTAL_SHAPES);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }



    // ================== KS1 Task 2 Panel (Angle Type Recognition) ==================
    private JPanel createKS1Task2Panel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        AngleTask angleTask = new AngleTask(); // Angle task object

        JTextField angleInput = new JTextField(8); // Angle input field, limited to 8 characters
        angleInput.setFont(new Font("Arial", Font.PLAIN, 16));

        // Angle visualization panel: Draws a protractor and the input angle
        JPanel anglePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();
                int centerX = width / 2;
                int centerY = height / 2;
                int radius = Math.min(width, height) / 3; // Protractor radius

                // Draw protractor background
                g2d.setColor(new Color(248, 249, 250));
                Ellipse2D protractor = new Ellipse2D.Double(centerX - radius, centerY - radius,
                        radius * 2, radius * 2);
                g2d.fill(protractor);
                g2d.setColor(new Color(55, 65, 81)); // Dark gray border
                g2d.draw(protractor);

                // Draw scale marks (0-360 degrees, 10-degree intervals, 30-degree labels)
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                for (int i = 0; i < 360; i += 10) {
                    double rad = Math.toRadians(i);
                    int x1 = centerX + (int) ((radius - 8) * Math.cos(rad));
                    int y1 = centerY - (int) ((radius - 8) * Math.sin(rad));
                    int x2 = centerX + (int) (radius * Math.cos(rad));
                    int y2 = centerY - (int) (radius * Math.sin(rad));
                    g2d.setStroke(new BasicStroke(i % 30 == 0 ? 2 : 1)); // Thicker main scale marks
                    g2d.drawLine(x1, y1, x2, y2);
                    if (i % 30 == 0) { // Display scale values
                        int textX = centerX + (int) ((radius - 25) * Math.cos(rad));
                        int textY = centerY - (int) ((radius - 25) * Math.sin(rad));
                        g2d.drawString(Integer.toString(i), textX, textY);
                    }
                }

                // Draw center point and baseline (horizontal center line)
                g2d.setColor(new Color(239, 68, 68)); // Red center point
                g2d.fillOval(centerX - 4, centerY - 4, 8, 8);
                g2d.setColor(new Color(55, 65, 81)); // Dark gray baseline
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(centerX - radius, centerY, centerX + radius, centerY);

                // Draw the user's input angle line (only if the angle is valid)
                if (angleTask.getCurrentAngle() > 0) {
                    g2d.setColor(new Color(24, 115, 204)); // Blue angle line
                    g2d.setStroke(new BasicStroke(3));
                    double angleRad = Math.toRadians(angleTask.getCurrentAngle());
                    int endX = centerX + (int) (radius * Math.cos(angleRad));
                    int endY = centerY - (int) (radius * Math.sin(angleRad));
                    g2d.drawLine(centerX, centerY, endX, endY); // Draw angle line
                }
            }
        };
        anglePanel.setPreferredSize(new Dimension(400, 400));
        anglePanel.setBorder(BorderFactory.createLineBorder(new Color(229, 231, 235), 2));
        anglePanel.setBackground(Color.WHITE);

        // Input area: Angle input field and submit button
        JPanel inputSection = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        inputSection.setBackground(Color.WHITE);
        JLabel inputLabel = new JLabel("Enter angle (0 - 360, multiples of 10°):");
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JButton submitBtn = new JButton("Submit Angle");
        submitBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        submitBtn.addActionListener(e -> {
            try {
                int angle = Integer.parseInt(angleInput.getText().trim());
                if (!angleTask.isValidInput(angle)) {
                    // Input validation: Must be a multiple of 10 between 0-360
                    JOptionPane.showMessageDialog(
                            panel,
                            "Invalid input! Please enter a number between 0 - 360 that's a multiple of 10.",
                            "Invalid Angle", JOptionPane.WARNING_MESSAGE);
                    angleInput.setText("");
                    return;
                }
                angleTask.setUserAngle(angle); // Set user's input angle value
                anglePanel.repaint(); // Refresh graphics display
                enableTypeButtons(true, panel); // Enable angle type selection buttons
                angleInput.setEnabled(false); // Disable input field after valid input
                submitBtn.setEnabled(false); // Disable submit button after valid input
            } catch (NumberFormatException ex) {
                // Handle non-numeric input
                JOptionPane.showMessageDialog(panel,
                        "Please enter a valid number (e.g., 30, 90, 180)",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                angleInput.setText("");
            }
        });
        inputSection.add(inputLabel);
        inputSection.add(angleInput);
        inputSection.add(submitBtn);

        // Angle type selection button area
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        typePanel.setBackground(Color.WHITE);
        typePanel.setName("typePanel");
        String[] types = {"Acute", "Right", "Obtuse", "Reflex"}; // List of angle types

        for (String type : types) {
            JButton btn = new JButton(type);
            btn.setName("typeBtn_" + type);
            btn.setFont(new Font("Arial", Font.PLAIN, 16));
            btn.setPreferredSize(new Dimension(120, 40));
            btn.setBackground(new Color(243, 244, 246));
            btn.setEnabled(false); // Initially disabled, enabled after angle input
            btn.addActionListener(e -> {
                // Handle answer submission
                handleAnswer(angleTask, type, panel, angleInput, submitBtn);
            });
            typePanel.add(btn);
        }

        // Home button: Return to home interface and reset task
        JButton homeBtn = new JButton("Home");
        homeBtn.addActionListener(e -> {
            angleTask.reset(); // Reset task state
            cardLayout.show(mainPanel, "home");
        });
        homeBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        homeBtn.setForeground(Color.WHITE);
        homeBtn.setBackground(new Color(70, 130, 180));
        homeBtn.setFocusPainted(false);
        homeBtn.setPreferredSize(new Dimension(150, 45));

        // Bottom panel layout: Type buttons on top, Home button at bottom
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 10));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setName("bottomPanel"); // Set panel name for easy lookup
        bottomPanel.add(typePanel, BorderLayout.NORTH); // Angle type buttons
        bottomPanel.add(homeBtn, BorderLayout.SOUTH);   // Home button
        panel.add(bottomPanel, BorderLayout.SOUTH);      // Add to bottom area
        panel.add(inputSection, BorderLayout.NORTH);     // Input area on top
        panel.add(anglePanel, BorderLayout.CENTER);      // Graphics panel in the middle

        return panel;
    }

    /**
     * Enables or disables angle type selection buttons
     * @param enable Whether to enable
     * @param parentPanel Parent panel (for finding child components)
     */
    private void enableTypeButtons(boolean enable, JPanel parentPanel) {
        // Traverse parent panel components to find bottom panel and type panel
        for (Component component : parentPanel.getComponents()) {
            if (component instanceof JPanel && "bottomPanel".equals(component.getName())) {
                JPanel bottomPanel = (JPanel) component;
                for (Component childComponent : bottomPanel.getComponents()) {
                    if (childComponent instanceof JPanel && "typePanel".equals(childComponent.getName())) {
                        JPanel typePanel = (JPanel) childComponent;
                        // Traverse buttons in type panel to set enable state
                        for (Component btnComponent : typePanel.getComponents()) {
                            if (btnComponent instanceof JButton) {
                                JButton button = (JButton) btnComponent;
                                button.setEnabled(enable);
                            }
                        }
                        return; // Return immediately after finding
                    }
                }
            }
        }
        System.out.println("Type panel not found!"); // Debug message: Type panel not found
    }

    /**
     * Handles angle type answers
     * @param angleTask Angle task object
     * @param selectedType Selected angle type
     * @param panel Current panel
     * @param angleInput Angle input field
     * @param submitBtn Submit button
     */
    private void handleAnswer(AngleTask angleTask, String selectedType, JPanel panel, JTextField angleInput, JButton submitBtn) {
        String correctType = getAngleType(angleTask.getCurrentAngle()); // Get correct angle type
        boolean isCorrect = selectedType.equals(correctType); // Determine if correct

        if (isCorrect) {
            // Check if this angle type has already been completed
            if (angleTask.isAngleTypeCompleted(correctType)) {
                JOptionPane.showMessageDialog(mainFrame,
                        "You have already completed this angle type! Try a different angle.",
                        "Already Completed",
                        JOptionPane.INFORMATION_MESSAGE);
                // Reset for next angle
                angleTask.reset(); // Reset task state
                angleInput.setText(""); // Clear input field
                angleInput.setEnabled(true); // Re-enable input field
                submitBtn.setEnabled(true); // Re-enable submit button
                enableTypeButtons(false, panel); // Disable type buttons
                return;
            }

            int attemptsUsed = 3 - angleTask.getAttemptsLeft();
            int pointsToAdd = 0;

            switch (attemptsUsed) {
                case 0: pointsToAdd = 3; break; // First try
                case 1: pointsToAdd = 2; break; // Second try
                case 2: pointsToAdd = 1; break; // Third try
            }

            score += pointsToAdd;
            if (scoreLabel != null) {
                scoreLabel.setText("Current Score: " + score);
            }

            JOptionPane.showMessageDialog(mainFrame,
                    "Great job! You earned " + pointsToAdd + " points!",
                    "Correct!",
                    JOptionPane.INFORMATION_MESSAGE);

            angleTask.markAngleTypeCompleted(correctType); // Mark this angle type as completed
            angleTask.incrementCorrectCount(); // Increment correct count
            progress += 5; // Increase progress
            if (progress > 100) progress = 100; // Progress does not exceed 100%

            // Check if all angle types have been completed
            if (angleTask.getCorrectCount() >= 4) {
                JOptionPane.showMessageDialog(mainFrame,
                        "Congratulations! You have completed all angle types!",
                        "Task Completed",
                        JOptionPane.INFORMATION_MESSAGE);
                completeTask("ks1_task2"); // Mark task as completed
                cardLayout.show(mainPanel, "home"); // Return to home interface
                angleTask.resetCorrectCount(); // Reset correct count
            } else {
                // Reset for next angle
                angleTask.reset(); // Reset task state
                angleInput.setText(""); // Clear input field
                angleInput.setEnabled(true); // Re-enable input field
                submitBtn.setEnabled(true); // Re-enable submit button
                enableTypeButtons(false, panel); // Disable type buttons
            }
        } else {
            angleTask.decrementAttempts();
            if (angleTask.getAttemptsLeft() > 0) {
                JOptionPane.showMessageDialog(mainFrame,
                        "Incorrect! You have " + angleTask.getAttemptsLeft() + " attempts left.",
                        "Wrong Answer",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainFrame,
                        "Incorrect! The correct answer was: " + correctType,
                        "Wrong Answer",
                        JOptionPane.ERROR_MESSAGE);

                // Reset for next angle after all attempts are used
                angleTask.reset(); // Reset task state
                angleInput.setText(""); // Clear input field
                angleInput.setEnabled(true); // Re-enable input field
                submitBtn.setEnabled(true); // Re-enable submit button
                enableTypeButtons(false, panel); // Disable type buttons
            }
        }
    }

    /**
     * Angle task inner class, managing angle input and correct count
     */
    private class AngleTask {
        private int userAngle;            // User's input angle value
        private int correctCount = 0;     // Number of correct answers
        private int attemptsLeft = 3;     // Number of attempts left
        private Set<String> completedAngleTypes = new HashSet<>(); // Track completed angle types

        public int getCorrectCount() { return correctCount; }
        public void incrementCorrectCount() { correctCount++; }
        public void resetCorrectCount() { correctCount = 0; }

        public int getCurrentAngle() { return userAngle; }
        public void setUserAngle(int angle) { this.userAngle = angle; }

        public int getAttemptsLeft() { return attemptsLeft; }
        public void decrementAttempts() { attemptsLeft--; }

        public boolean isAngleTypeCompleted(String angleType) {
            return completedAngleTypes.contains(angleType);
        }

        public void markAngleTypeCompleted(String angleType) {
            completedAngleTypes.add(angleType);
        }

        /**
         * Validates input angle (0-360 and multiple of 10)
         * @param angle Input angle value
         * @return Whether valid
         */
        public boolean isValidInput(int angle) {
            return angle >= 0 && angle <= 360 && angle % 10 == 0;
        }

        /**
         * Resets task state (angle value to zero and attempts to 3)
         */
        public void reset() {
            this.userAngle = 0;
            this.attemptsLeft = 3;
        }
    }



    /**
     * Creates the KS2 Task 3 panel for shape area calculation
     * This panel allows users to practice calculating areas of different shapes
     * @return The configured shape area calculation panel
     */
    private JPanel createShapeAreaCalculationPanel() {
        ShapeAreaCalculationPanel panel = new ShapeAreaCalculationPanel();
        panel.backToMenuButton.addActionListener(e -> cardLayout.show(mainPanel, "home"));
        return panel;
    }


    class ShapeAreaCalculationPanel extends JPanel {
        // Format for decimal numbers
        private static final DecimalFormat df = new DecimalFormat("#.##");
        private Random random = new Random();

        // UI Components
        private JLabel shapeInfoLabel;
        private JLabel formulaLabel;
        private JTextField answerField;
        private JButton submitButton;
        private JLabel feedbackLabel;
        private JButton backToMenuButton;
        private JPanel shapeSelectionPanel;
        private JPanel calculationPanel;
        private CardLayout cardLayout;
        private JLabel timerLabel;

        // Task state variables
        private Set<String> completedShapes;
        private String currentShapeType;
        private double correctAnswer;
        private int attemptsLeft;
        private double dim1, dim2, dim3;

        // Drawing and timing components
        private DrawingPanel drawingPanel;
        private Timer timer;
        private int questionTime = 180;
        private boolean taskCompleted = false;
        private boolean isCalculationInProgress = false;

        /**
         * Constructor: Initializes the panel layout and components
         */
        public ShapeAreaCalculationPanel() {
            setLayout(new BorderLayout());
            completedShapes = new HashSet<>();

            // Initialize UI panels
            createShapeSelectionPanel();
            createCalculationPanel();

            // Setup card layout for switching between panels
            JPanel cardPanel = new JPanel();
            cardLayout = new CardLayout();
            cardPanel.setLayout(cardLayout);
            cardPanel.add(shapeSelectionPanel, "selection");
            cardPanel.add(calculationPanel, "calculation");
            add(cardPanel, BorderLayout.CENTER);

            // Show selection panel initially
            cardLayout.show(cardPanel, "selection");

            // Add component listeners for panel state management
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentShown(ComponentEvent e) {
                    SwingUtilities.invokeLater(() -> {
                        resetAndShowSelectionPanel();
                    });
                }

                @Override
                public void componentHidden(ComponentEvent e) {
                    SwingUtilities.invokeLater(() -> {
                        stopTimer();
                        isCalculationInProgress = false;
                    });
                }
            });
        }

        /**
         * Creates the shape selection panel with buttons for different shapes
         */
        private void createShapeSelectionPanel() {
            shapeSelectionPanel = new JPanel();
            shapeSelectionPanel.setLayout(new BoxLayout(shapeSelectionPanel, BoxLayout.Y_AXIS));
            shapeSelectionPanel.setBackground(Color.WHITE);
            shapeSelectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Add title
            JLabel titleLabel = new JLabel("Select a Shape to Calculate Area");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            shapeSelectionPanel.add(titleLabel);
            shapeSelectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));

            // Create grid of shape buttons
            JPanel gridPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            gridPanel.setBackground(Color.WHITE);
            gridPanel.setMaximumSize(new Dimension(600, 400));

            // Add buttons for each shape type
            String[] shapes = {"Rectangle", "Parallelogram", "Triangle", "Trapezium"};
            for (String shape : shapes) {
                JButton shapeButton = createShapeButton(shape);
                gridPanel.add(shapeButton);
            }

            shapeSelectionPanel.add(gridPanel);
            shapeSelectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));

            // Add back to menu button
            backToMenuButton = new JButton("Back to Main Menu");
            backToMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            backToMenuButton.setMaximumSize(new Dimension(200, 50));
            backToMenuButton.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> {
                    stopTimer();
                    isCalculationInProgress = false;
                    ShapevilleApp.this.cardLayout.show(ShapevilleApp.this.mainPanel, "home");
                });
            });
            shapeSelectionPanel.add(backToMenuButton);
        }

        /**
         * Creates a button for selecting a specific shape
         * @param shapeName The name of the shape
         * @return The configured button
         */
        private JButton createShapeButton(String shapeName) {
            JButton button = new JButton(shapeName);
            button.setPreferredSize(new Dimension(200, 150));
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setEnabled(!completedShapes.contains(shapeName));

            button.addActionListener(e -> {
                if (!isCalculationInProgress && !completedShapes.contains(shapeName)) {
                    currentShapeType = shapeName;
                    startShapeCalculation();
                }
            });

            return button;
        }

        /**
         * Creates the calculation panel with input fields and shape visualization
         */
        private void createCalculationPanel() {
            calculationPanel = new JPanel(new BorderLayout());
            calculationPanel.setBackground(Color.WHITE);

            // Create top panel with timer and instructions
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
            topPanel.setBackground(Color.WHITE);
            topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Add timer label
            timerLabel = new JLabel("Remaining time: 180 seconds", SwingConstants.CENTER);
            timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
            timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            topPanel.add(timerLabel);

            // Add shape info label
            shapeInfoLabel = new JLabel("Shape information will be displayed here", SwingConstants.CENTER);
            shapeInfoLabel.setFont(new Font("Arial", Font.BOLD, 18));
            shapeInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            topPanel.add(shapeInfoLabel);

            calculationPanel.add(topPanel, BorderLayout.NORTH);

            // Create center panel with shape drawing
            drawingPanel = new DrawingPanel();
            drawingPanel.setPreferredSize(new Dimension(300, 200));
            JPanel centerPanel = new JPanel(new BorderLayout());
            centerPanel.setBackground(Color.WHITE);
            centerPanel.add(drawingPanel, BorderLayout.CENTER);
            calculationPanel.add(centerPanel, BorderLayout.CENTER);

            // Create bottom panel with input and controls
            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
            bottomPanel.setBackground(Color.WHITE);
            bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Add input field
            JPanel inputPanel = new JPanel(new FlowLayout());
            inputPanel.setBackground(Color.WHITE);
            inputPanel.add(new JLabel("Enter area:"));
            answerField = new JTextField(10);
            inputPanel.add(answerField);
            bottomPanel.add(inputPanel);

            // Add submit button
            submitButton = new JButton("Submit Answer");
            submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            submitButton.setMaximumSize(new Dimension(200, 50));
            submitButton.addActionListener(e -> checkAnswer());
            bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            bottomPanel.add(submitButton);

            // Add feedback label
            feedbackLabel = new JLabel(" ", SwingConstants.CENTER);
            feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            bottomPanel.add(feedbackLabel);

            // Add back button
            JButton backToSelectionButton = new JButton("Back to Shape Selection");
            backToSelectionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            backToSelectionButton.setMaximumSize(new Dimension(200, 50));
            backToSelectionButton.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> {
                    if (isCalculationInProgress) {
                        int confirm = JOptionPane.showConfirmDialog(
                                this,
                                "Are you sure you want to return to shape selection?\nYour current progress will be lost.",
                                "Confirm Return",
                                JOptionPane.YES_NO_OPTION
                        );
                        if (confirm == JOptionPane.YES_OPTION) {
                            resetAndShowSelectionPanel();
                        }
                    } else {
                        resetAndShowSelectionPanel();
                    }
                });
            });
            bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            bottomPanel.add(backToSelectionButton);

            calculationPanel.add(bottomPanel, BorderLayout.SOUTH);
        }

        /**
         * Resets the panel state and shows the selection panel
         */
        private void resetAndShowSelectionPanel() {
            SwingUtilities.invokeLater(() -> {
                stopTimer();
                isCalculationInProgress = false;
                answerField.setText("");
                feedbackLabel.setText(" ");
                updateShapeButtons();
                cardLayout.show((Container)getComponent(0), "selection");

                // Check if all shapes are completed
                if (completedShapes.size() >= 4 && !taskCompleted) {
                    taskCompleted = true;
                    completeTask("ks2_task3");
                    JOptionPane.showMessageDialog(this,
                            "Congratulations! You have completed all shape calculations!",
                            "Task Completed",
                            JOptionPane.INFORMATION_MESSAGE);
                    ShapevilleApp.this.cardLayout.show(ShapevilleApp.this.mainPanel, "home");
                }

                revalidate();
                repaint();
            });
        }

        /**
         * Updates the enabled state of shape buttons based on completion status
         */
        private void updateShapeButtons() {
            try {
                for (Component comp : shapeSelectionPanel.getComponents()) {
                    if (comp instanceof JPanel && ((JPanel) comp).getLayout() instanceof GridLayout) {
                        JPanel gridPanel = (JPanel) comp;
                        for (Component btnComp : gridPanel.getComponents()) {
                            if (btnComp instanceof JButton) {
                                JButton button = (JButton) btnComp;
                                String buttonText = button.getText();
                                button.setEnabled(!completedShapes.contains(buttonText));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Error updating shape buttons: " + e.getMessage());
            }
        }

        /**
         * Starts a new shape calculation task
         */
        private void startShapeCalculation() {
            SwingUtilities.invokeLater(() -> {
                try {
                    isCalculationInProgress = true;
                    questionTime = 180;
                    timerLabel.setText("Remaining time: 180 seconds");
                    attemptsLeft = 3;
                    answerField.setText("");
                    answerField.setEnabled(true);
                    submitButton.setEnabled(true);
                    feedbackLabel.setText(" ");
                    feedbackLabel.setForeground(Color.BLACK);

                    String shapeText = "";
                    dim1 = dim2 = dim3 = 0;

                    // Generate dimensions and formula based on shape type
                    switch (currentShapeType) {
                        case "Rectangle":
                            dim1 = random.nextInt(19) + 2;
                            dim2 = random.nextInt(19) + 2;
                            correctAnswer = dim1 * dim2;
                            shapeText = String.format("Rectangle: length = %s cm, width = %s cm", df.format(dim1), df.format(dim2));
                            break;
                        case "Parallelogram":
                            dim1 = random.nextInt(19) + 2;
                            dim2 = random.nextInt(19) + 2;
                            correctAnswer = dim1 * dim2;
                            shapeText = String.format("Parallelogram: base = %s cm, height = %s cm", df.format(dim1), df.format(dim2));
                            break;
                        case "Triangle":
                            dim1 = random.nextInt(19) + 2;
                            dim2 = random.nextInt(19) + 2;
                            correctAnswer = (dim1 * dim2) / 2.0;
                            shapeText = String.format("Triangle: base = %s cm, height = %s cm", df.format(dim1), df.format(dim2));
                            break;
                        case "Trapezium":
                            dim1 = random.nextInt(10) + 2;
                            dim2 = random.nextInt(10) + dim1 + 1;
                            dim3 = random.nextInt(19) + 2;
                            correctAnswer = ((dim1 + dim2) / 2.0) * dim3;
                            shapeText = String.format("Trapezoid: upper base a = %s cm, lower base b = %s cm, height h = %s cm",
                                    df.format(dim1), df.format(dim2), df.format(dim3));
                            break;
                    }

                    shapeInfoLabel.setText(shapeText);
                    drawingPanel.setShapeProperties(currentShapeType, dim1, dim2, dim3);
                    drawingPanel.repaint();

                    cardLayout.show((Container)getComponent(0), "calculation");
                    revalidate();
                    repaint();

                    startTimer();
                } catch (Exception e) {
                    System.err.println("Error starting shape calculation: " + e.getMessage());
                    resetAndShowSelectionPanel();
                }
            });
        }

        /**
         * Starts the timer for the current calculation task
         */
        private void startTimer() {
            SwingUtilities.invokeLater(() -> {
                stopTimer();
                timer = new Timer(1000, e -> {
                    questionTime--;
                    if (SwingUtilities.isEventDispatchThread()) {
                        timerLabel.setText("Remaining time: " + questionTime + " seconds");
                        if (questionTime <= 0) {
                            handleTimeout();
                        }
                    } else {
                        SwingUtilities.invokeLater(() -> {
                            timerLabel.setText("Remaining time: " + questionTime + " seconds");
                            if (questionTime <= 0) {
                                handleTimeout();
                            }
                        });
                    }
                });
                timer.start();
            });
        }

        /**
         * Stops the current timer
         */
        private void stopTimer() {
            if (timer != null) {
                timer.stop();
                timer = null;
            }
        }

        /**
         * Handles timeout event for the current calculation task
         */
        private void handleTimeout() {
            SwingUtilities.invokeLater(() -> {
                stopTimer();
                isCalculationInProgress = false;
                String formula = getFormulaWithValues();
                feedbackLabel.setText("Time's up! " + formula);
                feedbackLabel.setForeground(Color.RED);
                submitButton.setEnabled(false);
                answerField.setEnabled(false);

                // Return to selection panel after delay
                Timer delayTimer = new Timer(2000, e -> resetAndShowSelectionPanel());
                delayTimer.setRepeats(false);
                delayTimer.start();
            });
        }

        /**
         * Gets the formula with values for the current shape
         * @return The formula string with values
         */
        private String getFormulaWithValues() {
            switch (currentShapeType) {
                case "Rectangle":
                    return String.format("Area = length × width = %s × %s = %.2f cm²",
                            df.format(dim1), df.format(dim2), correctAnswer);
                case "Parallelogram":
                    return String.format("Area = base × height = %s × %s = %.2f cm²",
                            df.format(dim1), df.format(dim2), correctAnswer);
                case "Triangle":
                    return String.format("Area = (base × height) ÷ 2 = (%s × %s) ÷ 2 = %.2f cm²",
                            df.format(dim1), df.format(dim2), correctAnswer);
                case "Trapezium":
                    return String.format("Area = ((a + b) ÷ 2) × h = ((%s + %s) ÷ 2) × %s = %.2f cm²",
                            df.format(dim1), df.format(dim2), df.format(dim3), correctAnswer);
                default:
                    return "";
            }
        }

        /**
         * Checks the user's answer against the correct answer
         */
        private void checkAnswer() {
            if (attemptsLeft <= 0 || !isCalculationInProgress) return;

            try {
                double userAnswer = Double.parseDouble(answerField.getText());
                if (Math.abs(userAnswer - correctAnswer) < 0.01) {
                    int pointsToAdd = 0;
                    int attemptsUsed = 3 - attemptsLeft;

                    // Calculate points based on attempts used
                    switch (attemptsUsed) {
                        case 0: pointsToAdd = 3; break; // First try
                        case 1: pointsToAdd = 2; break; // Second try
                        case 2: pointsToAdd = 1; break; // Third try
                    }

                    score += pointsToAdd;
                    if (scoreLabel != null) {
                        scoreLabel.setText("Current Score: " + score);
                    }

                    JOptionPane.showMessageDialog(mainFrame,
                            "Great job! You earned " + pointsToAdd + " points!",
                            "Correct!",
                            JOptionPane.INFORMATION_MESSAGE);

                    feedbackLabel.setText("Correct answer!");
                    feedbackLabel.setForeground(Color.GREEN);
                    submitButton.setEnabled(false);
                    answerField.setEnabled(false);
                    completedShapes.add(currentShapeType);
                    isCalculationInProgress = false;

                    // Check if all shapes are completed
                    if (completedShapes.size() >= 4 && !taskCompleted) {
                        taskCompleted = true;
                        completeTask("ks2_task3");
                        JOptionPane.showMessageDialog(this,
                                "Congratulations! You have completed all shape calculations!",
                                "Task Completed",
                                JOptionPane.INFORMATION_MESSAGE);
                        stopTimer();
                        ShapevilleApp.this.cardLayout.show(ShapevilleApp.this.mainPanel, "home");
                    } else {
                        // Return to selection panel after delay
                        Timer delayTimer = new Timer(1500, e -> resetAndShowSelectionPanel());
                        delayTimer.setRepeats(false);
                        delayTimer.start();
                    }
                } else {
                    attemptsLeft--;
                    feedbackLabel.setForeground(Color.RED);
                    if (attemptsLeft > 0) {
                        feedbackLabel.setText(String.format("Wrong answer. %d attempts left.", attemptsLeft));
                    } else {
                        String formula = getFormulaWithValues();
                        feedbackLabel.setText("Wrong answer. " + formula);
                        submitButton.setEnabled(false);
                        answerField.setEnabled(false);
                        isCalculationInProgress = false;
                        // Return to selection panel after delay
                        Timer delayTimer = new Timer(3000, e -> resetAndShowSelectionPanel());
                        delayTimer.setRepeats(false);
                        delayTimer.start();
                    }
                }
            } catch (NumberFormatException e) {
                feedbackLabel.setText("Please enter a valid number.");
                feedbackLabel.setForeground(Color.ORANGE);
            }
        }

        /**
         * Panel for drawing shapes with their dimensions
         */
        class DrawingPanel extends JPanel {
            private String shapeType;
            private double d1, d2, d3;
            private final int PADDING = 25;

            /**
             * Sets the properties for the shape to be drawn
             * @param type The type of shape
             * @param dim1 First dimension
             * @param dim2 Second dimension
             * @param dim3 Third dimension (for trapezoid)
             */
            public void setShapeProperties(String type, double dim1, double dim2, double dim3) {
                this.shapeType = type;
                this.d1 = dim1;
                this.d2 = dim2;
                this.d3 = dim3;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (shapeType == null) return;

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.BLUE);
                g2d.setStroke(new BasicStroke(2));

                int panelWidth = getWidth();
                int panelHeight = getHeight();
                int drawWidth = panelWidth - 2 * PADDING;
                int drawHeight = panelHeight - 2 * PADDING;

                // Calculate scaling factor based on shape dimensions
                double maxDim = Math.max(d1, Math.max(d2, d3));
                if (shapeType.equals("Trapezium")) maxDim = Math.max(d2, d3);
                else if (shapeType.equals("Triangle") || shapeType.equals("Parallelogram")) maxDim = Math.max(d1, d2);
                else if (shapeType.equals("Rectangle")) maxDim = Math.max(d1, d2);
                if (maxDim <= 0) maxDim = 10;

                double scale = Math.min(drawWidth / maxDim, drawHeight / maxDim) * 0.8;
                if (shapeType.equals("Rectangle")) {
                    scale = Math.min(drawWidth / d1, drawHeight / d2) * 0.9;
                }

                // Scale dimensions for drawing
                int sD1 = (int) (d1 * scale);
                int sD2 = (int) (d2 * scale);
                int sD3 = (int) (d3 * scale);
                int startX = PADDING;
                int startY = PADDING;
                g2d.setColor(Color.decode("#4A90E2"));

                // Draw the appropriate shape based on type
                switch (shapeType) {
                    case "Rectangle":
                        if (sD1 > 0 && sD2 > 0) {
                            g2d.fillRect(startX + (drawWidth - sD1)/2, startY + (drawHeight - sD2)/2, sD1, sD2);
                            drawDimensionLabel(g2d, String.format("L: %.1f", d1),
                                    (int)(startX + (drawWidth - sD1)/2.0 + sD1/2.0),
                                    startY + (drawHeight - sD2)/2 + sD2 + 15, true);
                            drawDimensionLabel(g2d, String.format("W: %.1f", d2),
                                    (int)(startX + (drawWidth - sD1)/2.0 - 20),
                                    (int)(startY + (drawHeight - sD2)/2.0 + sD2/2.0), false);
                        }
                        break;
                    case "Parallelogram":
                        if (sD1 > 0 && sD2 > 0) {
                            Path2D.Double parallelogram = new Path2D.Double();
                            int xOffset = sD2 / 2;
                            if (startX + sD1 + xOffset > panelWidth - PADDING) {
                                xOffset = Math.max(0, (panelWidth - PADDING - startX - sD1));
                            }
                            int pStartX = startX + (drawWidth - (sD1 + xOffset))/2;
                            int pStartY = startY + (drawHeight - sD2)/2;
                            parallelogram.moveTo(pStartX + xOffset, pStartY);
                            parallelogram.lineTo(pStartX + sD1 + xOffset, pStartY);
                            parallelogram.lineTo(pStartX + sD1, pStartY + sD2);
                            parallelogram.lineTo(pStartX, pStartY + sD2);
                            parallelogram.closePath();
                            g2d.fill(parallelogram);
                            drawDimensionLabel(g2d, String.format("Base: %.1f", d1),
                                    (int)(pStartX + sD1/2.0),
                                    pStartY + sD2 + 15, true);
                            drawDimensionLabel(g2d, String.format("H: %.1f", d2),
                                    pStartX - 20,
                                    (int)(pStartY + sD2/2.0), false);
                        }
                        break;
                    case "Triangle":
                        if (sD1 > 0 && sD2 > 0) {
                            Path2D.Double triangle = new Path2D.Double();
                            int tStartX = startX + (drawWidth - sD1)/2;
                            int tStartY = startY + (drawHeight - sD2)/2;
                            triangle.moveTo(tStartX + sD1 / 2.0, tStartY);
                            triangle.lineTo(tStartX + sD1, tStartY + sD2);
                            triangle.lineTo(tStartX, tStartY + sD2);
                            triangle.closePath();
                            g2d.fill(triangle);
                            drawDimensionLabel(g2d, String.format("Base: %.1f", d1),
                                    (int)(tStartX + sD1/2.0),
                                    tStartY + sD2 + 15, true);
                            drawDimensionLabel(g2d, String.format("H: %.1f", d2),
                                    (int)(tStartX + sD1/2.0 + 5),
                                    (int)(tStartY + sD2/2.0), false);
                        }
                        break;
                    case "Trapezium":
                        if (sD1 > 0 && sD2 > 0 && sD3 > 0 && sD2 > sD1) {
                            Path2D.Double trapezium = new Path2D.Double();
                            int zStartX = startX + (drawWidth - sD2)/2;
                            int zStartY = startY + (drawHeight - sD3)/2;
                            double topOffset = (sD2 - sD1) / 2.0;
                            trapezium.moveTo(zStartX + topOffset, zStartY);
                            trapezium.lineTo(zStartX + topOffset + sD1, zStartY);
                            trapezium.lineTo(zStartX + sD2, zStartY + sD3);
                            trapezium.lineTo(zStartX, zStartY + sD3);
                            trapezium.closePath();
                            g2d.fill(trapezium);
                            drawDimensionLabel(g2d, String.format("a: %.1f", d1),
                                    (int)(zStartX + topOffset + sD1/2.0),
                                    zStartY - 5, true);
                            drawDimensionLabel(g2d, String.format("b: %.1f", d2),
                                    (int)(zStartX + sD2/2.0),
                                    zStartY + sD3 + 15, true);
                            drawDimensionLabel(g2d, String.format("h: %.1f", d3),
                                    zStartX + sD2 + 5,
                                    (int)(zStartY + sD3/2.0), false);
                        }
                        break;
                }
            }

            /**
             * Draws dimension labels for the shape
             * @param g2d Graphics context
             * @param text Label text
             * @param x X coordinate
             * @param y Y coordinate
             * @param isHorizontal Whether the label is horizontal
             */
            private void drawDimensionLabel(Graphics2D g2d, String text, int x, int y, boolean isHorizontal) {
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(text);
                if (isHorizontal) {
                    g2d.drawString(text, x - textWidth / 2, y);
                } else {
                    g2d.drawString(text, x, y + fm.getAscent()/2);
                }
            }
        }
    }


    /**
     * Creates the KS2 Task 4 panel for circle calculations
     * This panel allows users to practice calculating circle area and circumference
     * @return The configured circle calculation panel
     */
    private JPanel createCircleCalculationPanel() {
        return new CircleCalculationPanel();
    }

    /**
     * Panel class for handling circle area and circumference calculations
     * Manages the UI and logic for calculating circle measurements
     */
    class CircleCalculationPanel extends JPanel {
        // Circle properties and state variables
        private int[] radius = new int[1]; // Circle radius (array form for access in drawing panel)
        private int attempts = 0;           // Number of attempts
        private JTextField calculationField; // Input field for calculation
        private JPanel circlePanel;         // Circle drawing panel
        private Timer timer;
        private int remainingTime = 180;
        private JLabel timerLabel;
        private String calculationType = ""; // "area" or "arc"
        private JPanel calculationPanel;     // Panel for calculation input
        private JPanel selectionPanel;       // Panel for selecting calculation type
        private CardLayout cardLayout;       // For switching between selection and calculation
        private JButton backToMainButton;    // Button to return to main menu
        private boolean taskCompleted = false; // Flag to track if task is completed

        /**
         * Constructor: Initialize panel layout and components
         */
        public CircleCalculationPanel() {
            cardLayout = new CardLayout();
            setLayout(cardLayout);
            setBackground(Color.WHITE);

            // Initialize UI panels
            createSelectionPanel();
            createCalculationPanel();

            // Add panels to main layout
            add(selectionPanel, "selection");
            add(calculationPanel, "calculation");

            // Show selection panel initially
            cardLayout.show(this, "selection");

            // Reset task when component is shown
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentShown(ComponentEvent e) {
                    reset();
                }
            });
        }

        /**
         * Creates the selection panel with calculation type options
         */
        private void createSelectionPanel() {
            selectionPanel = new JPanel();
            selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
            selectionPanel.setBackground(Color.WHITE);
            selectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Add title and buttons
            JLabel titleLabel = new JLabel("Select Calculation Type");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton areaButton = new JButton("Calculate Area");
            areaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            areaButton.setMaximumSize(new Dimension(200, 50));

            JButton arcButton = new JButton("Calculate Arc Length");
            arcButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            arcButton.setMaximumSize(new Dimension(200, 50));

            backToMainButton = new JButton("Back to Main Menu");
            backToMainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            backToMainButton.setMaximumSize(new Dimension(200, 50));

            // Layout components
            selectionPanel.add(Box.createVerticalGlue());
            selectionPanel.add(titleLabel);
            selectionPanel.add(Box.createRigidArea(new Dimension(0, 30)));
            selectionPanel.add(areaButton);
            selectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            selectionPanel.add(arcButton);
            selectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            selectionPanel.add(backToMainButton);
            selectionPanel.add(Box.createVerticalGlue());

            // Add action listeners
            areaButton.addActionListener(e -> {
                calculationType = "area";
                updateCalculationPanel();
                cardLayout.show(this, "calculation");
            });

            arcButton.addActionListener(e -> {
                calculationType = "arc";
                updateCalculationPanel();
                cardLayout.show(this, "calculation");
            });

            backToMainButton.addActionListener(e -> returnToMainMenu());
        }

        /**
         * Returns to main menu and handles task completion
         */
        private void returnToMainMenu() {
            stopTimer();
            if (taskCompleted) {
                completeTask("ks2_task4");
            }
            reset();
            ShapevilleApp.this.cardLayout.show(ShapevilleApp.this.mainPanel, "home");
        }

        /**
         * Stops the current timer
         */
        private void stopTimer() {
            if (timer != null) {
                timer.stop();
                timer = null;
            }
        }

        /**
         * Creates the calculation panel with input fields and circle visualization
         */
        private void createCalculationPanel() {
            calculationPanel = new JPanel(new BorderLayout());
            calculationPanel.setBackground(Color.WHITE);

            // Create top panel with timer and instructions
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
            topPanel.setBackground(Color.WHITE);
            topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            timerLabel = new JLabel("Remaining time: 180 seconds", SwingConstants.CENTER);
            timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
            timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            topPanel.add(timerLabel);

            JLabel instruction = new JLabel("Calculate the circle measurement:");
            instruction.setFont(new Font("Arial", Font.BOLD, 18));
            instruction.setAlignmentX(Component.CENTER_ALIGNMENT);
            topPanel.add(instruction);
            calculationPanel.add(topPanel, BorderLayout.NORTH);

            // Create circle drawing panel
            circlePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    int width = getWidth();
                    int height = getHeight();
                    int centerX = width / 2;
                    int centerY = height / 2;

                    // Draw circle with radius line
                    g2d.setColor(new Color(200, 255, 200));
                    Ellipse2D circle = new Ellipse2D.Double(centerX - radius[0], centerY - radius[0], radius[0] * 2, radius[0] * 2);
                    g2d.fill(circle);
                    g2d.setColor(Color.BLACK);
                    g2d.draw(circle);

                    g2d.setStroke(new BasicStroke(2.0f));
                    g2d.drawLine(centerX, centerY, centerX + radius[0], centerY);
                    g2d.setFont(new Font("Arial", Font.PLAIN, 14));
                    g2d.drawString("Radius: " + radius[0]/4 + "cm", centerX + radius[0] / 2, centerY - 10);
                }
            };
            circlePanel.setPreferredSize(new Dimension(300, 300));
            circlePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            circlePanel.setBackground(Color.WHITE);

            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(Color.WHITE);
            centerPanel.add(circlePanel);
            calculationPanel.add(centerPanel, BorderLayout.CENTER);

            // Create bottom panel with input and controls
            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
            bottomPanel.setBackground(Color.WHITE);
            bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 40, 20));

            // Add input field and buttons
            JPanel inputPanel = new JPanel(new FlowLayout());
            inputPanel.setBackground(Color.WHITE);
            calculationField = new JTextField(10);
            calculationField.setFont(new Font("Arial", Font.PLAIN, 16));
            inputPanel.add(new JLabel("Enter value:"));
            inputPanel.add(calculationField);

            JButton submitButton = new JButton("Submit");
            submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
            submitButton.addActionListener(e -> handleSubmission());

            JButton backToSelectionButton = new JButton("Back to Selection");
            backToSelectionButton.setFont(new Font("Arial", Font.PLAIN, 16));
            backToSelectionButton.addActionListener(e -> {
                stopTimer();
                cardLayout.show(this, "selection");
            });

            bottomPanel.add(inputPanel);
            bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            bottomPanel.add(submitButton);
            bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            bottomPanel.add(backToSelectionButton);

            calculationPanel.add(bottomPanel, BorderLayout.SOUTH);
        }

        /**
         * Updates the calculation panel based on selected calculation type
         */
        private void updateCalculationPanel() {
            String instructionText = calculationType.equals("area") ?
                    "Calculate the area of the circle:" :
                    "Calculate the arc length (circumference) of the circle:";

            // Update instruction text
            for (Component comp : ((JPanel)calculationPanel.getComponent(0)).getComponents()) {
                if (comp instanceof JLabel && ((JLabel)comp).getFont().getSize() == 18) {
                    ((JLabel)comp).setText(instructionText);
                    break;
                }
            }

            calculationField.setText("");
            startTimer();
        }

        /**
         * Starts the timer for the current calculation task
         */
        private void startTimer() {
            remainingTime = 180;
            timerLabel.setText("Remaining time: 180 seconds");
            stopTimer();
            timer = new Timer(1000, e -> {
                remainingTime--;
                timerLabel.setText(String.format("Remaining time: %d seconds", remainingTime));
                if (remainingTime <= 0) {
                    stopTimer();
                    handleTimeout();
                }
            });
            timer.start();
        }

        /**
         * Handles timeout event for the current calculation task
         */
        private void handleTimeout() {
            double correctAnswer = calculationType.equals("area") ?
                    Math.PI * Math.pow(radius[0]/4.0, 2) :
                    2 * Math.PI * (radius[0]/4.0);

            String message = String.format("Time's up! Correct %s = %.2f %s",
                    calculationType.equals("area") ? "area" : "arc length",
                    correctAnswer,
                    calculationType.equals("area") ? "cm²" : "cm");

            JOptionPane.showMessageDialog(mainFrame, message, "Time's Up", JOptionPane.INFORMATION_MESSAGE);

            calculationField.setEnabled(false);
            cardLayout.show(this, "selection");
        }

        /**
         * Handles user's answer submission
         */
        private void handleSubmission() {
            try {
                double userAnswer = Double.parseDouble(calculationField.getText());
                double correctAnswer = calculationType.equals("area") ?
                        Math.PI * Math.pow(radius[0]/4.0, 2) :
                        2 * Math.PI * (radius[0]/4.0);

                if (Math.abs(userAnswer - correctAnswer) < 0.01) {
                    // Correct answer handling
                    stopTimer();
                    int pointsToAdd = 0;
                    switch (attempts) {
                        case 0: pointsToAdd = 3; break; // First try
                        case 1: pointsToAdd = 2; break; // Second try
                        case 2: pointsToAdd = 1; break; // Third try
                    }

                    score += pointsToAdd;
                    if (scoreLabel != null) {
                        scoreLabel.setText("Current Score: " + score);
                    }

                    JOptionPane.showMessageDialog(mainFrame,
                            "Great job! You earned " + pointsToAdd + " points!",
                            "Correct!",
                            JOptionPane.INFORMATION_MESSAGE);

                    taskCompleted = true;
                    JOptionPane.showMessageDialog(mainFrame,
                            "Correct! Returning to main menu.",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    returnToMainMenu();
                } else {
                    // Incorrect answer handling
                    attempts++;
                    if (attempts >= 3) {
                        stopTimer();
                        String formula = calculationType.equals("area") ?
                                "Area = π×r² = %.2f cm²" :
                                "Arc Length = 2πr = %.2f cm";
                        JOptionPane.showMessageDialog(mainFrame,
                                String.format("Three attempts failed!\nCorrect formula:\n" + formula,
                                        correctAnswer),
                                "Solution", JOptionPane.INFORMATION_MESSAGE);
                        cardLayout.show(this, "selection");
                    } else {
                        JOptionPane.showMessageDialog(mainFrame,
                                "Incorrect! Remaining attempts: " + (3 - attempts),
                                "Error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame,
                        "Invalid input! Please enter a number.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            calculationField.setText("");
        }

        /**
         * Resets the panel state for a new calculation
         */
        private void reset() {
            attempts = 0;
            remainingTime = 180;
            taskCompleted = false;
            timerLabel.setText("Remaining time: 180 seconds");
            stopTimer();
            updateCircleRadius(radius);
            circlePanel.repaint();
            cardLayout.show(this, "selection");
            calculationField.setEnabled(true);
        }

        /**
         * Updates the circle radius with a new random value
         * @param radius Array to store the radius value
         */
        private void updateCircleRadius(int[] radius) {
            radius[0] = (random.nextInt(19) + 2) * 4; // Generate radius between 2-20cm, multiply by 4 for display scaling
        }
    }
    /**
     * Creates the bonus task panel for composite figure calculations
     * This panel allows users to practice calculating areas of composite shapes
     * @return The configured composite figure calculation panel
     */
    class BonusTaskCompositePanel extends JPanel {
        // UI Components
        private JLabel instructionLabel;
        private JLabel timerLabel;
        private Timer timer;
        private int remainingTime = 180;
        private JPanel shapeSelectionPanel;
        private JPanel calculationPanel;
        private CardLayout cardLayout;
        private JButton backToMainButton;
        private JTextField answerField;
        private JButton submitButton;
        private JLabel feedbackLabel;
        private JLabel currentShapeLabel;
        private JLabel currentShapeImageLabel;

        // Task state variables
        private Set<Integer> completedShapes = new HashSet<>();
        private final int[] allShapeIds = {2, 3, 4, 5, 8, 9};
        private int currentShapeId;
        private double currentCorrectArea;
        private Random rand = new Random();
        private int attempts = 0;
        private boolean taskCompleted = false;
        private static final int MAX_ATTEMPTS = 3;

        /**
         * Constructor: Initialize panel layout and components
         */
        public BonusTaskCompositePanel() {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);

            // Initialize UI panels
            cardLayout = new CardLayout();
            JPanel mainCardPanel = new JPanel(cardLayout);
            mainCardPanel.setBackground(Color.WHITE);

            createShapeSelectionPanel();
            createCalculationPanel();

            mainCardPanel.add(shapeSelectionPanel, "selection");
            mainCardPanel.add(calculationPanel, "calculation");

            add(mainCardPanel, BorderLayout.CENTER);
            cardLayout.show(mainCardPanel, "selection");

            // Reset task when component is shown
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentShown(ComponentEvent e) {
                    resetTask();
                }
            });
        }

        /**
         * Creates the shape selection panel with composite figure options
         */
        private void createShapeSelectionPanel() {
            shapeSelectionPanel = new JPanel(new BorderLayout());
            shapeSelectionPanel.setBackground(Color.WHITE);
            shapeSelectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Add title and shape grid
            JLabel titleLabel = new JLabel("Select a Composite Figure to Calculate", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            shapeSelectionPanel.add(titleLabel, BorderLayout.NORTH);

            JPanel gridPanel = new JPanel(new GridLayout(2, 3, 20, 20));
            gridPanel.setBackground(Color.WHITE);

            // Create buttons for each shape
            for (int shapeId : allShapeIds) {
                JButton shapeButton = createShapeButton(shapeId);
                gridPanel.add(shapeButton);
            }

            JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            centerPanel.setBackground(Color.WHITE);
            centerPanel.add(gridPanel);
            shapeSelectionPanel.add(centerPanel, BorderLayout.CENTER);

            // Add back button
            backToMainButton = new JButton("Back to Main Menu");
            backToMainButton.setFont(new Font("Arial", Font.PLAIN, 16));
            backToMainButton.addActionListener(e -> {
                if (timer != null) {
                    timer.stop();
                }
                if (taskCompleted) {
                    completeTask("bonus_task_composite");
                }
                ShapevilleApp.this.cardLayout.show(ShapevilleApp.this.mainPanel, "home");
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.add(backToMainButton);
            shapeSelectionPanel.add(buttonPanel, BorderLayout.SOUTH);
        }

        /**
         * Creates a button for selecting a specific composite shape
         * @param shapeId The ID of the shape
         * @return The configured button
         */
        private JButton createShapeButton(int shapeId) {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(200, 150));
            button.setLayout(new BorderLayout());
            button.setEnabled(!completedShapes.contains(shapeId));

            // Load and display shape image
            try {
                String imagePath = String.format("/images1/shape%d.png", shapeId);
                BufferedImage image = ImageIO.read(Objects.requireNonNull(ShapevilleApp.class.getResource(imagePath)));
                Image scaledImage = image.getScaledInstance(180, 130, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                button.add(imageLabel, BorderLayout.CENTER);

                JLabel idLabel = new JLabel("Figure " + shapeId, SwingConstants.CENTER);
                button.add(idLabel, BorderLayout.SOUTH);
            } catch (Exception ex) {
                button.setText("Figure " + shapeId);
                System.err.println("Error loading image for shape " + shapeId + ": " + ex.getMessage());
            }

            button.addActionListener(e -> {
                if (!completedShapes.contains(shapeId)) {
                    currentShapeId = shapeId;
                    startCalculation();
                }
            });

            return button;
        }

        /**
         * Creates the calculation panel with input fields and shape visualization
         */
        private void createCalculationPanel() {
            calculationPanel = new JPanel(new BorderLayout());
            calculationPanel.setBackground(Color.WHITE);
            calculationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Create top panel with timer and instructions
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
            topPanel.setBackground(Color.WHITE);

            timerLabel = new JLabel("Remaining time: 180 seconds", SwingConstants.CENTER);
            timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
            timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            topPanel.add(timerLabel);

            instructionLabel = new JLabel("Calculate the area of the composite figure:", SwingConstants.CENTER);
            instructionLabel.setFont(new Font("Arial", Font.BOLD, 18));
            instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            topPanel.add(instructionLabel);

            calculationPanel.add(topPanel, BorderLayout.NORTH);

            // Create center panel with shape display
            JPanel centerPanel = new JPanel(new BorderLayout());
            centerPanel.setBackground(Color.WHITE);

            currentShapeLabel = new JLabel("", SwingConstants.CENTER);
            currentShapeLabel.setFont(new Font("Arial", Font.BOLD, 16));
            centerPanel.add(currentShapeLabel, BorderLayout.NORTH);

            currentShapeImageLabel = new JLabel();
            currentShapeImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            currentShapeImageLabel.setPreferredSize(new Dimension(300, 200));
            currentShapeImageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            centerPanel.add(currentShapeImageLabel, BorderLayout.CENTER);

            calculationPanel.add(centerPanel, BorderLayout.CENTER);

            // Create bottom panel with input and controls
            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
            bottomPanel.setBackground(Color.WHITE);

            // Add input field and buttons
            JPanel inputPanel = new JPanel(new FlowLayout());
            inputPanel.setBackground(Color.WHITE);
            inputPanel.add(new JLabel("Area:"));
            answerField = new JTextField(10);
            inputPanel.add(answerField);
            bottomPanel.add(inputPanel);

            submitButton = new JButton("Submit");
            submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
            submitButton.addActionListener(e -> handleSubmit());
            bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            bottomPanel.add(submitButton);

            feedbackLabel = new JLabel(" ", SwingConstants.CENTER);
            feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            bottomPanel.add(feedbackLabel);

            JButton backToSelectionButton = new JButton("Back to Selection");
            backToSelectionButton.setFont(new Font("Arial", Font.PLAIN, 16));
            backToSelectionButton.addActionListener(e -> {
                if (timer != null) {
                    timer.stop();
                }
                cardLayout.show((Container)getComponent(0), "selection");
            });
            bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            bottomPanel.add(backToSelectionButton);

            calculationPanel.add(bottomPanel, BorderLayout.SOUTH);
        }

        /**
         * Resets the task state and updates UI
         */
        private void resetTask() {
            completedShapes.clear();
            scoreLabel.setText("Current Score: " + score);
            updateShapeButtons();
            cardLayout.show((Container)getComponent(0), "selection");
        }

        /**
         * Updates the enabled state of shape buttons based on completion status
         */
        private void updateShapeButtons() {
            Component[] components = ((JPanel)((JPanel)shapeSelectionPanel.getComponent(1)).getComponent(0)).getComponents();
            for (Component comp : components) {
                if (comp instanceof JButton) {
                    JButton button = (JButton)comp;
                    String buttonText = button.getText();
                    if (buttonText.startsWith("Figure ")) {
                        int shapeId = Integer.parseInt(buttonText.substring(7));
                        button.setEnabled(!completedShapes.contains(shapeId));
                    }
                }
            }
        }

        /**
         * Starts a new calculation task for the selected shape
         */
        private void startCalculation() {
            currentCorrectArea = calculateShapeArea(currentShapeId);
            attempts = 0;
            remainingTime = 300;
            timerLabel.setText("Remaining time: 300 seconds");
            answerField.setText("");
            answerField.setEnabled(true);
            submitButton.setEnabled(true);
            feedbackLabel.setText(" ");
            feedbackLabel.setForeground(Color.BLACK);

            // Update instruction and image
            instructionLabel.setText(String.format("Calculate the area of figure %d:", currentShapeId));
            currentShapeLabel.setText(String.format("Figure %d", currentShapeId));

            try {
                String imagePath = String.format("/images1/shape%d.png", currentShapeId);
                BufferedImage image = ImageIO.read(Objects.requireNonNull(ShapevilleApp.class.getResource(imagePath)));
                Image scaledImage = image.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
                currentShapeImageLabel.setIcon(new ImageIcon(scaledImage));
            } catch (Exception ex) {
                currentShapeImageLabel.setIcon(null);
                currentShapeImageLabel.setText("Image loading failed!");
                System.err.println("Error loading image: " + ex.getMessage());
            }

            cardLayout.show((Container)getComponent(0), "calculation");
            startTimer();
        }

        /**
         * Starts the timer for the current calculation task
         */
        private void startTimer() {
            if (timer != null) {
                timer.stop();
            }
            timer = new Timer(1000, e -> {
                remainingTime--;
                timerLabel.setText(String.format("Remaining time: %d seconds", remainingTime));
                if (remainingTime <= 0) {
                    handleTimeout();
                }
            });
            timer.start();
        }

        /**
         * Handles timeout event for the current calculation task
         */
        private void handleTimeout() {
            if (timer != null) {
                timer.stop();
            }
            feedbackLabel.setText(String.format("Time's up! Correct area: %.2f", currentCorrectArea));
            feedbackLabel.setForeground(Color.RED);
            answerField.setEnabled(false);
            submitButton.setEnabled(false);

            new java.util.Timer().schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> {
                        cardLayout.show((Container)getComponent(0), "selection");
                        updateShapeButtons();
                    });
                }
            }, 3000);
        }

        /**
         * Handles user's answer submission
         */
        private void handleSubmit() {
            if (timer != null) {
                timer.stop();
            }
            try {
                double userAnswer = Double.parseDouble(answerField.getText());
                if (Math.abs(userAnswer - currentCorrectArea) < 0.01) {
                    // Correct answer handling
                    int pointsToAdd = 0;
                    switch (attempts) {
                        case 0: pointsToAdd = 6; break; // First try
                        case 1: pointsToAdd = 4; break; // Second try
                        case 2: pointsToAdd = 2; break; // Third try
                    }

                    score += pointsToAdd;
                    if (scoreLabel != null) {
                        scoreLabel.setText("Current Score: " + score);
                    }

                    JOptionPane.showMessageDialog(mainFrame,
                            "Great job! You earned " + pointsToAdd + " points!",
                            "Correct!",
                            JOptionPane.INFORMATION_MESSAGE);

                    completedShapes.add(currentShapeId);
                    feedbackLabel.setText("Correct! Score +" + pointsToAdd);
                    feedbackLabel.setForeground(Color.GREEN);

                    // Check if all shapes are completed
                    if (completedShapes.size() >= allShapeIds.length && !taskCompleted) {
                        taskCompleted = true;
                        completeTask("bonus_task_composite");
                        JOptionPane.showMessageDialog(this,
                                "Congratulations! You have completed all composite figure calculations!",
                                "Task Completed",
                                JOptionPane.INFORMATION_MESSAGE);
                        new java.util.Timer().schedule(new java.util.TimerTask() {
                            @Override
                            public void run() {
                                SwingUtilities.invokeLater(() -> {
                                    ShapevilleApp.this.cardLayout.show(ShapevilleApp.this.mainPanel, "home");
                                });
                            }
                        }, 2000);
                    } else {
                        new java.util.Timer().schedule(new java.util.TimerTask() {
                            @Override
                            public void run() {
                                SwingUtilities.invokeLater(() -> {
                                    cardLayout.show((Container)getComponent(0), "selection");
                                    updateShapeButtons();
                                });
                            }
                        }, 1500);
                    }
                } else {
                    // Incorrect answer handling
                    attempts++;
                    if (attempts >= MAX_ATTEMPTS) {
                        feedbackLabel.setText(String.format("Wrong answer! Correct area: %.2f", currentCorrectArea));
                        feedbackLabel.setForeground(Color.RED);
                        answerField.setEnabled(false);
                        submitButton.setEnabled(false);

                        new java.util.Timer().schedule(new java.util.TimerTask() {
                            @Override
                            public void run() {
                                SwingUtilities.invokeLater(() -> {
                                    cardLayout.show((Container)getComponent(0), "selection");
                                    updateShapeButtons();
                                });
                            }
                        }, 3000);
                    } else {
                        feedbackLabel.setText(String.format("Wrong answer! %d attempts remaining.", MAX_ATTEMPTS - attempts));
                        feedbackLabel.setForeground(Color.ORANGE);
                        answerField.setText("");
                    }
                }
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Invalid input. Please enter a number!");
                feedbackLabel.setForeground(Color.ORANGE);
                startTimer();
            }
        }
    }

    /**
     * Creates the bonus task panel for sector calculations
     * This panel allows users to practice calculating sector areas and arc lengths
     * @return The configured sector calculation panel
     */

    class BonusTaskSectorPanel extends JPanel {
        // UI Components
        private JLabel instructionLabel;
        private JLabel timerLabel;
        private Timer timer;
        private int remainingTime = 300;
        private JPanel shapeSelectionPanel;
        private JPanel calculationPanel;
        private CardLayout cardLayout;
        private JButton backToMainButton;
        private JTextField areaField;
        private JButton submitButton;
        private JLabel feedbackLabel;
        private JLabel currentShapeLabel;
        private JLabel currentShapeImageLabel;

        // Task state variables
        private Set<Integer> completedSectors = new HashSet<>();
        private final int[] allSectorIds = {1, 2, 3, 4, 5, 6, 7, 8};
        private int currentSectorId;
        private double currentCorrectArea;
        private Random rand = new Random();
        private int attempts = 0;
        private boolean taskCompleted = false;
        private static final int MAX_ATTEMPTS = 3;

        /**
         * Constructor: Initialize panel layout and components
         */
        public BonusTaskSectorPanel() {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);

            // Initialize UI panels
            cardLayout = new CardLayout();
            JPanel mainCardPanel = new JPanel(cardLayout);
            mainCardPanel.setBackground(Color.WHITE);

            createShapeSelectionPanel();
            createCalculationPanel();

            mainCardPanel.add(shapeSelectionPanel, "selection");
            mainCardPanel.add(calculationPanel, "calculation");

            add(mainCardPanel, BorderLayout.CENTER);
            cardLayout.show(mainCardPanel, "selection");

            // Reset task when component is shown
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentShown(ComponentEvent e) {
                    resetTask();
                }
            });
        }

        /**
         * Creates the shape selection panel with sector options
         */
        private void createShapeSelectionPanel() {
            shapeSelectionPanel = new JPanel(new BorderLayout());
            shapeSelectionPanel.setBackground(Color.WHITE);
            shapeSelectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Add title and sector grid
            JLabel titleLabel = new JLabel("Select a Sector to Calculate", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            shapeSelectionPanel.add(titleLabel, BorderLayout.NORTH);

            JPanel gridPanel = new JPanel(new GridLayout(2, 4, 20, 20));
            gridPanel.setBackground(Color.WHITE);

            // Create buttons for each sector
            for (int sectorId : allSectorIds) {
                JButton sectorButton = createSectorButton(sectorId);
                gridPanel.add(sectorButton);
            }

            JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            centerPanel.setBackground(Color.WHITE);
            centerPanel.add(gridPanel);
            shapeSelectionPanel.add(centerPanel, BorderLayout.CENTER);

            // Add back button
            backToMainButton = new JButton("Back to Main Menu");
            backToMainButton.setFont(new Font("Arial", Font.PLAIN, 16));
            backToMainButton.addActionListener(e -> {
                if (timer != null) {
                    timer.stop();
                }
                if (taskCompleted) {
                    completeTask("bonus_task_sector");
                }
                ShapevilleApp.this.cardLayout.show(ShapevilleApp.this.mainPanel, "home");
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.add(backToMainButton);
            shapeSelectionPanel.add(buttonPanel, BorderLayout.SOUTH);
        }

        /**
         * Creates a button for selecting a specific sector
         * @param sectorId The ID of the sector
         * @return The configured button
         */
        private JButton createSectorButton(int sectorId) {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(180, 130));
            button.setLayout(new BorderLayout());
            button.setEnabled(!completedSectors.contains(sectorId));

            // Load and display sector image
            try {
                String imagePath = String.format("/images2/sector%d.png", sectorId);
                BufferedImage image = ImageIO.read(Objects.requireNonNull(ShapevilleApp.class.getResource(imagePath)));
                Image scaledImage = image.getScaledInstance(160, 110, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                button.add(imageLabel, BorderLayout.CENTER);

                JLabel idLabel = new JLabel("Sector " + sectorId, SwingConstants.CENTER);
                button.add(idLabel, BorderLayout.SOUTH);
            } catch (Exception ex) {
                button.setText("Sector " + sectorId);
                System.err.println("Error loading image for sector " + sectorId + ": " + ex.getMessage());
            }

            button.addActionListener(e -> {
                if (!completedSectors.contains(sectorId)) {
                    currentSectorId = sectorId;
                    startCalculation();
                }
            });

            return button;
        }

        /**
         * Creates the calculation panel with input fields and sector visualization
         */
        private void createCalculationPanel() {
            calculationPanel = new JPanel(new BorderLayout());
            calculationPanel.setBackground(Color.WHITE);
            calculationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Create top panel with timer and instructions
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
            topPanel.setBackground(Color.WHITE);

            timerLabel = new JLabel("Remaining time: 300 seconds", SwingConstants.CENTER);
            timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
            timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            topPanel.add(timerLabel);

            instructionLabel = new JLabel("Calculate the area of the sector:", SwingConstants.CENTER);
            instructionLabel.setFont(new Font("Arial", Font.BOLD, 18));
            instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            topPanel.add(instructionLabel);

            calculationPanel.add(topPanel, BorderLayout.NORTH);

            // Create center panel with sector display
            JPanel centerPanel = new JPanel(new BorderLayout());
            centerPanel.setBackground(Color.WHITE);

            currentShapeLabel = new JLabel("", SwingConstants.CENTER);
            currentShapeLabel.setFont(new Font("Arial", Font.BOLD, 16));
            centerPanel.add(currentShapeLabel, BorderLayout.NORTH);

            currentShapeImageLabel = new JLabel();
            currentShapeImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            currentShapeImageLabel.setPreferredSize(new Dimension(300, 200));
            currentShapeImageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            centerPanel.add(currentShapeImageLabel, BorderLayout.CENTER);

            calculationPanel.add(centerPanel, BorderLayout.CENTER);

            // Create bottom panel with input and controls
            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
            bottomPanel.setBackground(Color.WHITE);

            // Add input field and buttons
            JPanel inputPanel = new JPanel(new FlowLayout());
            inputPanel.setBackground(Color.WHITE);
            inputPanel.add(new JLabel("Area:"));
            areaField = new JTextField(10);
            inputPanel.add(areaField);
            bottomPanel.add(inputPanel);

            submitButton = new JButton("Submit");
            submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
            submitButton.addActionListener(e -> handleSubmit());
            bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            bottomPanel.add(submitButton);

            feedbackLabel = new JLabel(" ", SwingConstants.CENTER);
            feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            bottomPanel.add(feedbackLabel);

            JButton backToSelectionButton = new JButton("Back to Selection");
            backToSelectionButton.setFont(new Font("Arial", Font.PLAIN, 16));
            backToSelectionButton.addActionListener(e -> {
                if (timer != null) {
                    timer.stop();
                }
                cardLayout.show((Container)getComponent(0), "selection");
            });
            bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            bottomPanel.add(backToSelectionButton);

            calculationPanel.add(bottomPanel, BorderLayout.SOUTH);
        }

        /**
         * Resets the task state and updates UI
         */
        private void resetTask() {
            completedSectors.clear();
            scoreLabel.setText("Current Score: " + score);
            updateSectorButtons();
            cardLayout.show((Container)getComponent(0), "selection");
        }

        /**
         * Updates the enabled state of sector buttons based on completion status
         */
        private void updateSectorButtons() {
            Component[] components = ((JPanel)((JPanel)shapeSelectionPanel.getComponent(1)).getComponent(0)).getComponents();
            for (Component comp : components) {
                if (comp instanceof JButton) {
                    JButton button = (JButton)comp;
                    String buttonText = button.getText();
                    if (buttonText.startsWith("Sector ")) {
                        int sectorId = Integer.parseInt(buttonText.substring(7));
                        button.setEnabled(!completedSectors.contains(sectorId));
                    }
                }
            }
        }

        /**
         * Starts a new calculation task for the selected sector
         */
        private void startCalculation() {
            double r = getRadius(currentSectorId);
            double theta = getAngle(currentSectorId);
            currentCorrectArea = 3.14 * r * r * (theta / 360.0);

            attempts = 0;
            remainingTime = 300;
            timerLabel.setText("Remaining time: 300 seconds");
            areaField.setText("");
            areaField.setEnabled(true);
            submitButton.setEnabled(true);
            feedbackLabel.setText(" ");
            feedbackLabel.setForeground(Color.BLACK);

            // Update instruction and image
            instructionLabel.setText(String.format("Sector %d: R=%.1f, Angle=%.0f°\nCalculate Area:",
                    currentSectorId, r, theta));
            currentShapeLabel.setText(String.format("Sector %d", currentSectorId));

            try {
                String imagePath = String.format("/images2/sector%d.png", currentSectorId);
                BufferedImage image = ImageIO.read(Objects.requireNonNull(ShapevilleApp.class.getResource(imagePath)));
                Image scaledImage = image.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
                currentShapeImageLabel.setIcon(new ImageIcon(scaledImage));
            } catch (Exception ex) {
                currentShapeImageLabel.setIcon(null);
                currentShapeImageLabel.setText("Image loading failed!");
                System.err.println("Error loading image: " + ex.getMessage());
            }

            cardLayout.show((Container)getComponent(0), "calculation");
            startTimer();
        }

        /**
         * Starts the timer for the current calculation task
         */
        private void startTimer() {
            if (timer != null) {
                timer.stop();
            }
            timer = new Timer(1000, e -> {
                remainingTime--;
                timerLabel.setText(String.format("Remaining time: %d seconds", remainingTime));
                if (remainingTime <= 0) {
                    handleTimeout();
                }
            });
            timer.start();
        }

        /**
         * Handles timeout event for the current calculation task
         */
        private void handleTimeout() {
            if (timer != null) {
                timer.stop();
            }
            double r = getRadius(currentSectorId);
            double theta = getAngle(currentSectorId);
            String formula = String.format("Area = π×r²×(θ/360) = 3.14×%.1f²×(%.0f/360) = %.2f", r, theta, currentCorrectArea);
            feedbackLabel.setText(String.format("Time's up! %s", formula));
            feedbackLabel.setForeground(Color.RED);
            areaField.setEnabled(false);
            submitButton.setEnabled(false);

            new java.util.Timer().schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> {
                        cardLayout.show((Container)getComponent(0), "selection");
                        updateSectorButtons();
                    });
                }
            }, 3000);
        }

        /**
         * Handles user's answer submission
         */
        private void handleSubmit() {
            if (timer != null) {
                timer.stop();
            }
            try {
                double userArea = Double.parseDouble(areaField.getText());

                if (Math.abs(userArea - currentCorrectArea) < 0.01) {
                    // Correct answer handling
                    int pointsToAdd = 0;
                    switch (attempts) {
                        case 0: pointsToAdd = 6; break; // First try
                        case 1: pointsToAdd = 4; break; // Second try
                        case 2: pointsToAdd = 2; break; // Third try
                    }

                    score += pointsToAdd;
                    if (scoreLabel != null) {
                        scoreLabel.setText("Current Score: " + score);
                    }

                    JOptionPane.showMessageDialog(mainFrame,
                            "Great job! You earned " + pointsToAdd + " points!",
                            "Correct!",
                            JOptionPane.INFORMATION_MESSAGE);

                    completedSectors.add(currentSectorId);
                    feedbackLabel.setText("Correct! Score +" + pointsToAdd);
                    feedbackLabel.setForeground(Color.GREEN);

                    // Check if all sectors are completed
                    if (completedSectors.size() >= allSectorIds.length && !taskCompleted) {
                        taskCompleted = true;
                        completeTask("bonus_task_sector");
                        JOptionPane.showMessageDialog(this,
                                "Congratulations! You have completed all sector calculations!",
                                "Task Completed",
                                JOptionPane.INFORMATION_MESSAGE);
                        new java.util.Timer().schedule(new java.util.TimerTask() {
                            @Override
                            public void run() {
                                SwingUtilities.invokeLater(() -> {
                                    ShapevilleApp.this.cardLayout.show(ShapevilleApp.this.mainPanel, "home");
                                });
                            }
                        }, 2000);
                    } else {
                        new java.util.Timer().schedule(new java.util.TimerTask() {
                            @Override
                            public void run() {
                                SwingUtilities.invokeLater(() -> {
                                    cardLayout.show((Container)getComponent(0), "selection");
                                    updateSectorButtons();
                                });
                            }
                        }, 1500);
                    }
                } else {
                    // Incorrect answer handling
                    attempts++;
                    if (attempts >= MAX_ATTEMPTS) {
                        double r = getRadius(currentSectorId);
                        double theta = getAngle(currentSectorId);
                        String formula = String.format("Area = π×r²×(θ/360) = 3.14×%.1f²×(%.0f/360) = %.2f", r, theta, currentCorrectArea);
                        feedbackLabel.setText(String.format("Wrong answer! %s", formula));
                        feedbackLabel.setForeground(Color.RED);
                        areaField.setEnabled(false);
                        submitButton.setEnabled(false);

                        new java.util.Timer().schedule(new java.util.TimerTask() {
                            @Override
                            public void run() {
                                SwingUtilities.invokeLater(() -> {
                                    cardLayout.show((Container)getComponent(0), "selection");
                                    updateSectorButtons();
                                });
                            }
                        }, 3000);
                    } else {
                        feedbackLabel.setText(String.format("Wrong answer! %d attempts remaining.", MAX_ATTEMPTS - attempts));
                        feedbackLabel.setForeground(Color.ORANGE);
                        areaField.setText("");
                    }
                }
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Invalid input. Please enter a number!");
                feedbackLabel.setForeground(Color.ORANGE);
                startTimer();
            }
        }
    }


    /**
     * Calculates the area of a composite shape based on its ID
     * @param id The ID of the shape
     * @return The calculated area
     */
    private double calculateShapeArea(int id) {
        switch (id) {
            case 2: // Rectangle + Square combination
                return 20 * 10 + 11 * 11;
            case 3: // Rectangle + Extension part
                return 16 * 16 + 18 * 19;
            case 4: // Multiple rectangle combination
                return 24 * 6 + 12 * 12;
            case 5: // Rectangle + Triangle
                return 4 * 3 + (4 * 3) / 2.0;
            case 8: // Large rectangle minus small rectangle
                return 60 * 36 + 36 * 36;
            case 9: // Rectangle + Square
                return 11 * 10 + 8 * 8;
            default:
                return 0;
        }
    }

    /**
     * Gets the radius for a sector based on its ID
     * @param id The ID of the sector
     * @return The radius value
     */
    private double getRadius(int id) {
        switch (id) {
            case 1: return 8;
            case 2: return 18;
            case 3: return 19;
            case 4: return 22;
            case 5: return 3.5;
            case 6: return 8;
            case 7: return 12;
            case 8: return 15;
            default: return 0;
        }
    }

    /**
     * Gets the angle for a sector based on its ID
     * @param id The ID of the sector
     * @return The angle value in degrees
     */
    private double getAngle(int id) {
        switch (id) {
            case 1: return 90;   // Right angle sector
            case 2: return 130;  // Obtuse angle sector
            case 3: return 240;  // Reflex angle sector
            case 4: return 110;  // Acute angle sector
            case 5: return 100;  // Acute angle sector
            case 6: return 270;  // Reflex angle sector (3/4 circle)
            case 7: return 280;  // Reflex angle sector
            case 8: return 250;  // Reflex angle sector
            default: return 0;
        }
    }

    /**
     * Shows feedback for user's answer
     * @param isCorrect Whether the answer is correct
     */
    private void showFeedback(boolean isCorrect) {
        String message = isCorrect ? "Correct! Well done! 🌟" : "Incorrect. Try again! 💪";
        showCartoonDialog(message, "Answer Feedback", isCorrect ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Shows a cartoon-styled dialog with the specified message
     * @param message The message to display
     * @param title The dialog title
     * @param messageType The type of message (e.g., INFORMATION_MESSAGE)
     */
    private void showCartoonDialog(String message, String title, int messageType) {
        UIManager.put("OptionPane.background", CARTOON_BACKGROUND);
        UIManager.put("Panel.background", CARTOON_BACKGROUND);
        UIManager.put("OptionPane.messageForeground", CARTOON_TEXT);
        UIManager.put("OptionPane.messageFont", CARTOON_TEXT_FONT);
        UIManager.put("OptionPane.buttonFont", CARTOON_BUTTON_FONT);
        UIManager.put("Button.background", CARTOON_BUTTON);
        UIManager.put("Button.foreground", CARTOON_TEXT);
        UIManager.put("Button.focusPainted", false);

        JOptionPane.showMessageDialog(mainFrame, message, title, messageType);

        // Reset UI defaults
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("OptionPane.messageFont", null);
        UIManager.put("OptionPane.buttonFont", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("Button.focusPainted", null);
    }

    /**
     * Creates a cartoon-styled text field
     * @param columns The number of columns in the text field
     * @return The configured text field
     */
    private JTextField createCartoonTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(CARTOON_TEXT_FONT);
        textField.setForeground(CARTOON_TEXT);
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARTOON_BORDER, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    /**
     * Creates a cartoon-styled label
     * @param text The label text
     * @return The configured label
     */
    private JLabel createCartoonLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(CARTOON_TEXT_FONT);
        label.setForeground(CARTOON_TEXT);
        return label;
    }
}