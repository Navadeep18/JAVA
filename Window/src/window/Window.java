package window;

import DBConnection.DBConnection;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Window extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color PRIMARY = new Color(25, 72, 120);
    private static final Color PRIMARY_LIGHT = new Color(42, 112, 175);
    private static final Color BACKGROUND = new Color(238, 245, 252);
    private static final Color CARD = Color.WHITE;
    private static final Color TEXT = new Color(35, 45, 55);
    private static final Color MUTED = new Color(105, 115, 125);

    private static final Color GREEN = new Color(35, 160, 95);
    private static final Color BLUE = new Color(45, 115, 205);
    private static final Color RED = new Color(215, 65, 65);
    private static final Color ORANGE = new Color(235, 145, 45);
    private static final Color PURPLE = new Color(125, 85, 190);

    private static final Color BORDER = new Color(205, 215, 225);
    private static final Color TABLE_HEADER = new Color(30, 82, 135);

    // =========================================================
    // INPUT FIELDS
    // =========================================================

    private JTextField txtId;
    private JTextField txtName;
    private JComboBox<String> cmbDepartment;
    private JComboBox<String> cmbDesignation;
    private JTextField txtDate;
    private JTextField txtSalary;
    private JTextField txtEmail;
    private JTextField txtPhone;

    private JRadioButton rbActive;
    private JRadioButton rbInactive;

    // =========================================================
    // BUTTONS
    // =========================================================

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnRefresh;
    private JButton btnSearch;

    // =========================================================
    // SEARCH
    // =========================================================

    private JTextField txtSearch;
    private JComboBox<String> cmbSearch;

    // =========================================================
    // TABLE
    // =========================================================

    private JTable table;
    private DefaultTableModel model;

    // =========================================================
    // STATUS
    // =========================================================

    private JLabel statusLabel;
    private JLabel recordCountLabel;
    private JLabel connectionLabel;

    // =========================================================
    // DAO
    // =========================================================

    private EmployeeDAO dao;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public Window() {

        dao = new EmployeeDAO();

        setTitle("Employee Information Management System");

        setSize(1300, 850);

        setMinimumSize(new Dimension(1100, 700));

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(BACKGROUND);

        createGUI();

        testConnection();

        loadData();
    }

    // =========================================================
    // CREATE GUI
    // =========================================================

    private void createGUI() {

        setLayout(new BorderLayout());

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header = new JPanel(new BorderLayout());

        header.setBackground(PRIMARY);

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 28, 20, 28
                )
        );

        // LEFT HEADER
        JPanel headerLeft = new JPanel();

        headerLeft.setLayout(
                new BoxLayout(
                        headerLeft,
                        BoxLayout.Y_AXIS
                )
        );

        headerLeft.setOpaque(false);

        JLabel title = new JLabel(
                "EMPLOYEE INFORMATION MANAGEMENT SYSTEM"
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        27
                )
        );

        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Employee Records  •  Database Management  •  JDBC + MySQL"
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        subtitle.setForeground(
                new Color(215, 230, 245)
        );

        headerLeft.add(title);

        headerLeft.add(
                Box.createVerticalStrut(6)
        );

        headerLeft.add(subtitle);

        header.add(
                headerLeft,
                BorderLayout.WEST
        );

        // RIGHT HEADER
        connectionLabel = new JLabel(
                "●  CHECKING DATABASE..."
        );

        connectionLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        connectionLabel.setForeground(Color.WHITE);

        header.add(
                connectionLabel,
                BorderLayout.EAST
        );

        add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // MAIN PANEL
        // =====================================================

        JPanel mainPanel = new JPanel(
                new BorderLayout(15, 15)
        );

        mainPanel.setBackground(BACKGROUND);

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 20, 10, 20
                )
        );

        // =====================================================
        // EMPLOYEE FORM CARD
        // =====================================================

        JPanel formCard = new JPanel(
                new BorderLayout()
        );

        formCard.setBackground(CARD);

        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                BORDER,
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                10, 15, 15, 15
                        )
                )
        );

        // FORM TITLE
        JLabel formTitle = new JLabel(
                "  EMPLOYEE DETAILS"
        );

        formTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        formTitle.setForeground(PRIMARY);

        formTitle.setBorder(
                BorderFactory.createEmptyBorder(
                        0, 0, 8, 0
                )
        );

        formCard.add(
                formTitle,
                BorderLayout.NORTH
        );

        // FORM CONTENT
        JPanel formContent = new JPanel(
                new GridBagLayout()
        );

        formContent.setBackground(CARD);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        6, 8, 6, 8
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        // ROW 0
        addField(
                formContent,
                gbc,
                0,
                0,
                "Employee ID",
                txtId = createTextField()
        );

        addField(
                formContent,
                gbc,
                2,
                0,
                "Employee Name",
                txtName = createTextField()
        );

        // ROW 1
        cmbDepartment =
                new JComboBox<>(
                        new String[]{
                                "IT",
                                "HR",
                                "Finance",
                                "Sales",
                                "Marketing",
                                "Operations"
                        }
                );

        styleComboBox(cmbDepartment);

        addField(
                formContent,
                gbc,
                0,
                1,
                "Department",
                cmbDepartment
        );

        cmbDesignation =
                new JComboBox<>(
                        new String[]{
                                "Developer",
                                "Manager",
                                "Accountant",
                                "Tester",
                                "HR Executive",
                                "Team Lead",
                                "Designer"
                        }
                );

        styleComboBox(cmbDesignation);

        addField(
                formContent,
                gbc,
                2,
                1,
                "Designation",
                cmbDesignation
        );

        // ROW 2
        txtDate = createTextField();

        txtDate.setText(
                new SimpleDateFormat(
                        "dd-MM-yyyy"
                ).format(new Date())
        );

        addField(
                formContent,
                gbc,
                0,
                2,
                "Joining Date",
                txtDate
        );

        addField(
                formContent,
                gbc,
                2,
                2,
                "Salary",
                txtSalary = createTextField()
        );

        // ROW 3
        addField(
                formContent,
                gbc,
                0,
                3,
                "Email",
                txtEmail = createTextField()
        );

        addField(
                formContent,
                gbc,
                2,
                3,
                "Phone",
                txtPhone = createTextField()
        );

        // =====================================================
        // STATUS
        // =====================================================

        JLabel statusText =
                createLabel("Status");

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 0.1;

        formContent.add(
                statusText,
                gbc
        );

        rbActive =
                new JRadioButton("Active");

        rbInactive =
                new JRadioButton("Inactive");

        rbActive.setBackground(CARD);
        rbInactive.setBackground(CARD);

        rbActive.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        rbInactive.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        rbActive.setFocusPainted(false);
        rbInactive.setFocusPainted(false);

        ButtonGroup group =
                new ButtonGroup();

        group.add(rbActive);
        group.add(rbInactive);

        rbActive.setSelected(true);

        JPanel statusPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                5,
                                0
                        )
                );

        statusPanel.setBackground(CARD);

        statusPanel.add(rbActive);
        statusPanel.add(rbInactive);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.weightx = 1;

        formContent.add(
                statusPanel,
                gbc
        );

        // =====================================================
        // BUTTON PANEL
        // =====================================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                12,
                                8
                        )
                );

        buttonPanel.setBackground(CARD);

        btnAdd =
                createButton(
                        "ADD",
                        GREEN
                );

        btnUpdate =
                createButton(
                        "UPDATE",
                        BLUE
                );

        btnDelete =
                createButton(
                        "DELETE",
                        RED
                );

        btnClear =
                createButton(
                        "CLEAR",
                        ORANGE
                );

        btnRefresh =
                createButton(
                        "REFRESH",
                        PURPLE
                );

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;

        formContent.add(
                buttonPanel,
                gbc
        );

        formCard.add(
                formContent,
                BorderLayout.CENTER
        );

        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        // =====================================================
        // SEARCH CARD
        // =====================================================

        JPanel searchCard =
                new JPanel(
                        new BorderLayout(
                                15,
                                0
                        )
                );

        searchCard.setBackground(CARD);

        searchCard.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                BORDER,
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                10, 15, 10, 15
                        )
                )
        );

        JLabel searchTitle =
                new JLabel(
                        "SEARCH EMPLOYEES"
                );

        searchTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        searchTitle.setForeground(PRIMARY);

        searchCard.add(
                searchTitle,
                BorderLayout.WEST
        );

        JPanel searchControls =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                8,
                                0
                        )
                );

        searchControls.setBackground(CARD);

        cmbSearch =
                new JComboBox<>(
                        new String[]{
                                "ID",
                                "Name",
                                "Department"
                        }
                );

        styleComboBox(cmbSearch);

        cmbSearch.setPreferredSize(
                new Dimension(
                        140,
                        36
                )
        );

        txtSearch =
                createTextField();

        txtSearch.setPreferredSize(
                new Dimension(
                        330,
                        36
                )
        );

        btnSearch =
                createButton(
                        "SEARCH",
                        BLUE
                );

        searchControls.add(cmbSearch);

        searchControls.add(txtSearch);

        searchControls.add(btnSearch);

        searchCard.add(
                searchControls,
                BorderLayout.CENTER
        );

        // =====================================================
        // TOP AREA
        // =====================================================

        JPanel topArea =
                new JPanel(
                        new BorderLayout(
                                0,
                                12
                        )
                );

        topArea.setBackground(BACKGROUND);

        topArea.add(
                formCard,
                BorderLayout.CENTER
        );

        topArea.add(
                searchCard,
                BorderLayout.SOUTH
        );

        // =====================================================
        // TABLE
        // =====================================================

        JPanel tableCard =
                new JPanel(
                        new BorderLayout()
                );

        tableCard.setBackground(CARD);

        tableCard.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                BORDER,
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                8, 8, 8, 8
                        )
                )
        );

        JPanel tableHeader =
                new JPanel(
                        new BorderLayout()
                );

        tableHeader.setBackground(CARD);

        tableHeader.setBorder(
                BorderFactory.createEmptyBorder(
                        2, 5, 8, 5
                )
        );

        JLabel recordsTitle =
                new JLabel(
                        "EMPLOYEE RECORDS"
                );

        recordsTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        recordsTitle.setForeground(PRIMARY);

        recordCountLabel =
                new JLabel(
                        "0 Records"
                );

        recordCountLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        recordCountLabel.setForeground(
                PRIMARY_LIGHT
        );

        tableHeader.add(
                recordsTitle,
                BorderLayout.WEST
        );

        tableHeader.add(
                recordCountLabel,
                BorderLayout.EAST
        );

        tableCard.add(
                tableHeader,
                BorderLayout.NORTH
        );

        // =====================================================
        // TABLE MODEL
        // =====================================================

        model =
                new DefaultTableModel(
                        new String[]{
                                "ID",
                                "Name",
                                "Department",
                                "Designation",
                                "Joining Date",
                                "Salary",
                                "Email",
                                "Phone",
                                "Status"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        table =
                new JTable(model);

        table.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        table.setRowHeight(34);

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        table.setShowGrid(true);

        table.setGridColor(
                new Color(
                        220,
                        228,
                        236
                )
        );

        table.setSelectionBackground(
                new Color(
                        210,
                        230,
                        250
                )
        );

        table.setSelectionForeground(TEXT);

        table.setIntercellSpacing(
                new Dimension(
                        1,
                        1
                )
        );

        // =====================================================
        // TABLE HEADER
        // =====================================================

        table.getTableHeader().setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        table.getTableHeader().setBackground(
                TABLE_HEADER
        );

        table.getTableHeader().setForeground(
                Color.WHITE
        );

        table.getTableHeader().setPreferredSize(
                new Dimension(
                        0,
                        40
                )
        );

        table.getTableHeader().setReorderingAllowed(
                false
        );

        // =====================================================
        // COLUMN WIDTHS
        // =====================================================

        int[] widths = {
                55,
                140,
                110,
                125,
                120,
                100,
                190,
                120,
                90
        };

        for (
                int i = 0;
                i < widths.length;
                i++
        ) {

            table.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(
                            widths[i]
                    );
        }

        // =====================================================
        // SORTING
        // =====================================================

        TableRowSorter<DefaultTableModel> sorter =
                new TableRowSorter<>(
                        model
                );

        table.setRowSorter(sorter);

        JScrollPane scrollPane =
                new JScrollPane(table);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        tableCard.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =====================================================
        // CENTER AREA
        // =====================================================

        JPanel center =
                new JPanel(
                        new BorderLayout(
                                0,
                                12
                        )
                );

        center.setBackground(BACKGROUND);

        center.add(
                topArea,
                BorderLayout.NORTH
        );

        center.add(
                tableCard,
                BorderLayout.CENTER
        );

        mainPanel.add(
                center,
                BorderLayout.CENTER
        );

        add(
                mainPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // FOOTER
        // =====================================================

        JPanel footer =
                new JPanel(
                        new BorderLayout()
                );

        footer.setBackground(
                new Color(
                        250,
                        252,
                        255
                )
        );

        footer.setBorder(
                BorderFactory.createCompoundBorder(
                        new MatteBorder(
                                1,
                                0,
                                0,
                                0,
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                8, 20, 8, 20
                        )
                )
        );

        statusLabel =
                new JLabel(
                        "Ready"
                );

        statusLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        statusLabel.setForeground(GREEN);

        JLabel footerText =
                new JLabel(
                        "Employee Management System  •  Java Swing  •  JDBC  •  MySQL"
                );

        footerText.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        footerText.setForeground(MUTED);

        footer.add(
                statusLabel,
                BorderLayout.WEST
        );

        footer.add(
                footerText,
                BorderLayout.EAST
        );

        add(
                footer,
                BorderLayout.SOUTH
        );

        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        btnAdd.addActionListener(
                e -> addEmployee()
        );

        btnUpdate.addActionListener(
                e -> updateEmployee()
        );

        btnDelete.addActionListener(
                e -> deleteEmployee()
        );

        btnClear.addActionListener(
                e -> clearFields()
        );

        btnRefresh.addActionListener(
                e -> loadData()
        );

        btnSearch.addActionListener(
                e -> searchEmployee()
        );

        txtSearch.addActionListener(
                e -> searchEmployee()
        );

        // =====================================================
        // TABLE CLICK
        // =====================================================

        table.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        if (
                                e.getClickCount() == 1
                        ) {

                            fillFields();
                        }
                    }
                }
        );
    }

    // =========================================================
    // CREATE TEXT FIELD
    // =========================================================

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        field.setPreferredSize(
                new Dimension(
                        240,
                        36
                )
        );

        field.setBackground(Color.WHITE);

        field.setForeground(TEXT);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                new Color(
                                        185,
                                        198,
                                        212
                                ),
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                5,
                                10,
                                5,
                                10
                        )
                )
        );

        return field;
    }

    // =========================================================
    // CREATE LABEL
    // =========================================================

    private JLabel createLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        label.setForeground(TEXT);

        return label;
    }

    // =========================================================
    // ADD FIELD
    // =========================================================

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int x,
            int y,
            String label,
            JComponent component
    ) {

        JLabel lbl =
                createLabel(label);

        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.weightx = 0.1;

        panel.add(
                lbl,
                gbc
        );

        gbc.gridx = x + 1;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.weightx = 1;

        panel.add(
                component,
                gbc
        );
    }

    // =========================================================
    // STYLE COMBO BOX
    // =========================================================

    private void styleComboBox(
            JComboBox<String> combo
    ) {

        combo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        combo.setPreferredSize(
                new Dimension(
                        240,
                        36
                )
        );

        combo.setBackground(Color.WHITE);

        combo.setForeground(TEXT);

        combo.setFocusable(false);
    }

    // =========================================================
    // BEAUTIFUL BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(text) {

                    @Override
                    protected void paintComponent(
                            Graphics g
                    ) {

                        Graphics2D g2 =
                                (Graphics2D) g.create();

                        g2.setRenderingHint(
                                RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON
                        );

                        Color currentColor;

                        if (!isEnabled()) {

                            currentColor =
                                    new Color(
                                            185,
                                            190,
                                            195
                                    );

                        } else if (
                                getModel().isPressed()
                        ) {

                            currentColor =
                                    color.darker();

                        } else if (
                                getModel().isRollover()
                        ) {

                            currentColor =
                                    color.brighter();

                        } else {

                            currentColor = color;
                        }

                        g2.setColor(
                                currentColor
                        );

                        g2.fillRoundRect(
                                0,
                                0,
                                getWidth(),
                                getHeight(),
                                14,
                                14
                        );

                        g2.dispose();

                        super.paintComponent(g);
                    }
                };

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(Color.WHITE);

        button.setBackground(color);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setContentAreaFilled(false);

        button.setOpaque(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        110,
                        40
                )
        );

        button.setMargin(
                new Insets(
                        5,
                        18,
                        5,
                        18
                )
        );

        return button;
    }

    // =========================================================
    // DATABASE CONNECTION
    // =========================================================

    private void testConnection() {

        try {

            DBConnection.getConnection();

            connectionLabel.setText(
                    "●  DATABASE CONNECTED"
            );

            connectionLabel.setForeground(
                    new Color(
                            130,
                            255,
                            170
                    )
            );

            statusLabel.setText(
                    "Database connected successfully"
            );

            statusLabel.setForeground(GREEN);

        } catch (SQLException e) {

            connectionLabel.setText(
                    "●  DATABASE OFFLINE"
            );

            connectionLabel.setForeground(
                    new Color(
                            255,
                            150,
                            150
                    )
            );

            statusLabel.setText(
                    "Database connection failed"
            );

            statusLabel.setForeground(RED);

            JOptionPane.showMessageDialog(
                    this,
                    "Cannot connect to database:\n"
                    + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // LOAD DATA
    // =========================================================

    private void loadData() {

        try {

            dao.loadEmployees(model);

            int count =
                    model.getRowCount();

            recordCountLabel.setText(
                    count + " Records"
            );

            statusLabel.setText(
                    "✓  " +
                    count +
                    " employee records loaded"
            );

            statusLabel.setForeground(GREEN);

            table.clearSelection();

            btnUpdate.setEnabled(false);

            btnDelete.setEnabled(false);

        } catch (SQLException e) {

            showDatabaseError(e);
        }
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    private boolean validateFields() {

        if (
                txtId.getText()
                        .trim()
                        .isEmpty()
        ) {

            showWarning(
                    "Employee ID cannot be empty."
            );

            txtId.requestFocus();

            return false;
        }

        if (
                txtName.getText()
                        .trim()
                        .isEmpty()
        ) {

            showWarning(
                    "Employee name cannot be empty."
            );

            txtName.requestFocus();

            return false;
        }

        if (
                txtSalary.getText()
                        .trim()
                        .isEmpty()
        ) {

            showWarning(
                    "Salary cannot be empty."
            );

            txtSalary.requestFocus();

            return false;
        }

        if (
                txtEmail.getText()
                        .trim()
                        .isEmpty()
        ) {

            showWarning(
                    "Email cannot be empty."
            );

            txtEmail.requestFocus();

            return false;
        }

        if (
                txtPhone.getText()
                        .trim()
                        .isEmpty()
        ) {

            showWarning(
                    "Phone number cannot be empty."
            );

            txtPhone.requestFocus();

            return false;
        }

        // ID
        try {

            Integer.parseInt(
                    txtId.getText()
                            .trim()
            );

        } catch (
                NumberFormatException e
        ) {

            showWarning(
                    "Employee ID must be a number."
            );

            txtId.requestFocus();

            return false;
        }

        // SALARY
        try {

            double salary =
                    Double.parseDouble(
                            txtSalary.getText()
                                    .trim()
                    );

            if (salary < 0) {

                showWarning(
                        "Salary cannot be negative."
                );

                txtSalary.requestFocus();

                return false;
            }

        } catch (
                NumberFormatException e
        ) {

            showWarning(
                    "Salary must be a valid number."
            );

            txtSalary.requestFocus();

            return false;
        }

        // EMAIL
        String email =
                txtEmail.getText()
                        .trim();

        if (
                !Pattern.matches(
                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
                        email
                )
        ) {

            showWarning(
                    "Enter a valid email address."
            );

            txtEmail.requestFocus();

            return false;
        }

        // PHONE
        String phone =
                txtPhone.getText()
                        .trim();

        if (
                !phone.matches(
                        "\\d{10}"
                )
        ) {

            showWarning(
                    "Phone number must contain exactly 10 digits."
            );

            txtPhone.requestFocus();

            return false;
        }

        // DATE
        if (
                !txtDate.getText()
                        .matches(
                                "\\d{2}-\\d{2}-\\d{4}"
                        )
        ) {

            showWarning(
                    "Date must be in DD-MM-YYYY format."
            );

            txtDate.requestFocus();

            return false;
        }

        return true;
    }

    // =========================================================
    // ADD EMPLOYEE
    // =========================================================

    private void addEmployee() {

        if (!validateFields()) {
            return;
        }

        try {

            int id =
                    Integer.parseInt(
                            txtId.getText()
                                    .trim()
                    );

            double salary =
                    Double.parseDouble(
                            txtSalary.getText()
                                    .trim()
                    );

            String date =
                    convertDate(
                            txtDate.getText()
                                    .trim()
                    );

            String status =
                    rbActive.isSelected()
                    ? "Active"
                    : "Inactive";

            dao.addEmployee(
                    id,
                    txtName.getText().trim(),
                    cmbDepartment
                            .getSelectedItem()
                            .toString(),
                    cmbDesignation
                            .getSelectedItem()
                            .toString(),
                    date,
                    salary,
                    txtEmail.getText().trim(),
                    txtPhone.getText().trim(),
                    status
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Employee added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            statusLabel.setText(
                    "✓  Employee added successfully"
            );

            statusLabel.setForeground(GREEN);

            clearFields();

            loadData();

        } catch (SQLException e) {

            showDatabaseError(e);
        }
    }

    // =========================================================
    // UPDATE EMPLOYEE
    // =========================================================

    private void updateEmployee() {

        if (!validateFields()) {
            return;
        }

        if (
                table.getSelectedRow() == -1
        ) {

            showWarning(
                    "Please select an employee from the table."
            );

            return;
        }

        try {

            int id =
                    Integer.parseInt(
                            txtId.getText()
                                    .trim()
                    );

            double salary =
                    Double.parseDouble(
                            txtSalary.getText()
                                    .trim()
                    );

            String date =
                    convertDate(
                            txtDate.getText()
                                    .trim()
                    );

            String status =
                    rbActive.isSelected()
                    ? "Active"
                    : "Inactive";

            dao.updateEmployee(
                    id,
                    txtName.getText().trim(),
                    cmbDepartment
                            .getSelectedItem()
                            .toString(),
                    cmbDesignation
                            .getSelectedItem()
                            .toString(),
                    date,
                    salary,
                    txtEmail.getText().trim(),
                    txtPhone.getText().trim(),
                    status
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Employee updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            statusLabel.setText(
                    "✓  Employee updated successfully"
            );

            statusLabel.setForeground(GREEN);

            clearFields();

            loadData();

        } catch (SQLException e) {

            showDatabaseError(e);
        }
    }

    // =========================================================
    // DELETE EMPLOYEE
    // =========================================================

    private void deleteEmployee() {

        if (
                table.getSelectedRow() == -1
        ) {

            showWarning(
                    "Please select an employee to delete."
            );

            return;
        }

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this employee?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                result != JOptionPane.YES_OPTION
        ) {

            return;
        }

        try {

            int id =
                    Integer.parseInt(
                            txtId.getText()
                                    .trim()
                    );

            dao.deleteEmployee(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Employee deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            statusLabel.setText(
                    "✓  Employee deleted successfully"
            );

            statusLabel.setForeground(GREEN);

            clearFields();

            loadData();

        } catch (SQLException e) {

            showDatabaseError(e);
        }
    }

    // =========================================================
    // SEARCH EMPLOYEE
    // =========================================================

    private void searchEmployee() {

        String value =
                txtSearch.getText()
                        .trim();

        if (value.isEmpty()) {

            loadData();

            return;
        }

        try {

            String field =
                    cmbSearch
                            .getSelectedItem()
                            .toString();

            dao.searchEmployees(
                    model,
                    field,
                    value
            );

            int count =
                    model.getRowCount();

            recordCountLabel.setText(
                    count + " Records"
            );

            statusLabel.setText(
                    "🔍  " +
                    count +
                    " matching records found"
            );

            statusLabel.setForeground(BLUE);

        } catch (SQLException e) {

            showDatabaseError(e);
        }
    }

    // =========================================================
    // FILL FIELDS FROM TABLE
    // =========================================================

    private void fillFields() {

        int row =
                table.getSelectedRow();

        if (row == -1) {
            return;
        }

        int modelRow =
                table.convertRowIndexToModel(
                        row
                );

        txtId.setText(
                model.getValueAt(
                        modelRow,
                        0
                ).toString()
        );

        txtName.setText(
                model.getValueAt(
                        modelRow,
                        1
                ).toString()
        );

        cmbDepartment.setSelectedItem(
                model.getValueAt(
                        modelRow,
                        2
                ).toString()
        );

        cmbDesignation.setSelectedItem(
                model.getValueAt(
                        modelRow,
                        3
                ).toString()
        );

        String date =
                model.getValueAt(
                        modelRow,
                        4
                ).toString();

        if (date.length() >= 10) {

            String[] parts =
                    date.substring(
                            0,
                            10
                    ).split("-");

            txtDate.setText(
                    parts[2]
                    + "-"
                    + parts[1]
                    + "-"
                    + parts[0]
            );
        }

        txtSalary.setText(
                model.getValueAt(
                        modelRow,
                        5
                ).toString()
        );

        txtEmail.setText(
                model.getValueAt(
                        modelRow,
                        6
                ).toString()
        );

        txtPhone.setText(
                model.getValueAt(
                        modelRow,
                        7
                ).toString()
        );

        String status =
                model.getValueAt(
                        modelRow,
                        8
                ).toString();

        if (
                status.equalsIgnoreCase(
                        "Active"
                )
        ) {

            rbActive.setSelected(true);

        } else {

            rbInactive.setSelected(true);
        }

        btnUpdate.setEnabled(true);

        btnDelete.setEnabled(true);

        statusLabel.setText(
                "●  Employee selected  •  Ready for Update / Delete"
        );

        statusLabel.setForeground(BLUE);
    }

    // =========================================================
    // CLEAR
    // =========================================================

    private void clearFields() {

        txtId.setText("");

        txtName.setText("");

        cmbDepartment.setSelectedIndex(0);

        cmbDesignation.setSelectedIndex(0);

        txtDate.setText(
                new SimpleDateFormat(
                        "dd-MM-yyyy"
                ).format(new Date())
        );

        txtSalary.setText("");

        txtEmail.setText("");

        txtPhone.setText("");

        rbActive.setSelected(true);

        table.clearSelection();

        btnUpdate.setEnabled(false);

        btnDelete.setEnabled(false);

        statusLabel.setText(
                "Ready"
        );

        statusLabel.setForeground(GREEN);

        txtId.requestFocus();
    }

    // =========================================================
    // DATE CONVERSION
    // =========================================================

    private String convertDate(
            String date
    ) {

        String[] parts =
                date.split("-");

        return parts[2]
                + "-"
                + parts[1]
                + "-"
                + parts[0];
    }

    // =========================================================
    // WARNING
    // =========================================================

    private void showWarning(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Input Validation",
                JOptionPane.WARNING_MESSAGE
        );
    }

    // =========================================================
    // DATABASE ERROR
    // =========================================================

    private void showDatabaseError(
            SQLException e
    ) {

        String message =
                e.getMessage();

        if (message == null) {

            message =
                    "Unknown database error.";
        }

        if (
                message.contains(
                        "Duplicate entry"
                )
        ) {

            message =
                    "Employee ID already exists.\n"
                    + "Please enter a different ID.";

        } else if (
                message.contains(
                        "doesn't exist"
                )
        ) {

            message =
                    "Employee table was not found.\n"
                    + "Please check the employee_db database.";

        } else if (
                message.contains(
                        "Communications link failure"
                )
        ) {

            message =
                    "Cannot connect to MySQL.\n"
                    + "Please make sure MySQL Server is running.";
        }

        JOptionPane.showMessageDialog(
                this,
                message,
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );

        statusLabel.setText(
                "✕  Database operation failed"
        );

        statusLabel.setForeground(RED);
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    try {

                        UIManager.setLookAndFeel(
                                UIManager
                                        .getSystemLookAndFeelClassName()
                        );

                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                    new Window()
                            .setVisible(true);
                }
        );
    }
}