package canteenmanagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class CanteenManagement extends JFrame {

    // =========================================================
    // DATABASE DETAILS
    // =========================================================

    private static final String URL =
            "jdbc:mysql://localhost:3306/canteen_db";

    private static final String USER = "root";

    // CHANGE THIS IF YOUR MYSQL PASSWORD IS DIFFERENT
    private static final String PASSWORD = "root";


    // =========================================================
    // COLORS
    // =========================================================

    private static final Color NAVY =
            new Color(25, 75, 135);

    private static final Color BLUE =
            new Color(52, 152, 219);

    private static final Color GREEN =
            new Color(39, 174, 96);

    private static final Color PURPLE =
            new Color(142, 68, 173);

    private static final Color ORANGE =
            new Color(243, 156, 18);

    private static final Color RED =
            new Color(192, 57, 43);

    private static final Color DARK =
            new Color(44, 62, 80);

    private static final Color BACKGROUND =
            new Color(245, 248, 252);


    // =========================================================
    // GUI COMPONENTS
    // =========================================================

    private JTextField customerField;
    private JTextField quantityField;

    private JComboBox<String> foodComboBox;

    private JTextPane outputArea;

    private JButton loadMenuButton;
    private JButton placeOrderButton;
    private JButton viewOrdersButton;
    private JButton adminButton;
    private JButton clearButton;
    private JButton exitButton;


    // =========================================================
    // COLLECTIONS
    // =========================================================

    private final ArrayList<FoodItem> foodList =
            new ArrayList<>();

    private final ArrayList<Order> orderList =
            new ArrayList<>();


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public CanteenManagement() {

        setTitle(
                "Canteen Food Ordering and Billing System"
        );

        setSize(1100, 700);

        setMinimumSize(
                new Dimension(950, 620)
        );

        setDefaultCloseOperation(
                JFrame.DO_NOTHING_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createGUI();

        addWindowListener(
                new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(
                            java.awt.event.WindowEvent e) {

                        confirmExit();
                    }
                }
        );

        loadMenu();
    }


    // =========================================================
    // DATABASE CONNECTION
    // =========================================================

    private Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }


    // =========================================================
    // CREATE GUI
    // =========================================================

    private void createGUI() {

        getContentPane().setBackground(
                BACKGROUND
        );

        setLayout(
                new BorderLayout(
                        0,
                        0
                )
        );


        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel();

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        header.setBackground(
                NAVY
        );

        header.setBorder(
                new EmptyBorder(
                        18,
                        20,
                        18,
                        20
                )
        );


        JLabel title =
                new JLabel(
                        "CANTEEN FOOD ORDERING & BILLING SYSTEM"
                );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        title.setForeground(
                Color.WHITE
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        27
                )
        );


        JLabel subtitle =
                new JLabel(
                        "Food  •  Orders  •  Billing  •  Stock Management"
                );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        subtitle.setForeground(
                new Color(
                        220,
                        235,
                        255
                )
        );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );


        header.add(title);

        header.add(
                Box.createVerticalStrut(6)
        );

        header.add(subtitle);


        add(
                header,
                BorderLayout.NORTH
        );


        // =====================================================
        // MAIN PANEL
        // =====================================================

        JPanel main =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        main.setBackground(
                BACKGROUND
        );

        main.setBorder(
                new EmptyBorder(
                        15,
                        15,
                        10,
                        15
                )
        );


        // =====================================================
        // LEFT ORDER PANEL
        // =====================================================

        JPanel orderPanel =
                new JPanel();

        orderPanel.setLayout(
                new BoxLayout(
                        orderPanel,
                        BoxLayout.Y_AXIS
                )
        );

        orderPanel.setBackground(
                Color.WHITE
        );

        orderPanel.setPreferredSize(
                new Dimension(
                        365,
                        450
                )
        );

        orderPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                new Color(
                                        210,
                                        220,
                                        235
                                ),
                                1,
                                true
                        ),
                        new EmptyBorder(
                                22,
                                22,
                                22,
                                22
                        )
                )
        );


        // TITLE

        JLabel orderTitle =
                createLabel(
                        "PLACE YOUR ORDER",
                        NAVY,
                        21
                );

        orderPanel.add(
                orderTitle
        );

        orderPanel.add(
                Box.createVerticalStrut(25)
        );


        // CUSTOMER NAME

        orderPanel.add(
                createLabel(
                        "Customer Name",
                        DARK,
                        14
                )
        );

        orderPanel.add(
                Box.createVerticalStrut(7)
        );

        customerField =
                new JTextField();

        styleTextField(
                customerField
        );

        orderPanel.add(
                customerField
        );

        orderPanel.add(
                Box.createVerticalStrut(20)
        );


        // FOOD ITEM

        orderPanel.add(
                createLabel(
                        "Food Item",
                        DARK,
                        14
                )
        );

        orderPanel.add(
                Box.createVerticalStrut(7)
        );

        foodComboBox =
                new JComboBox<>();

        foodComboBox.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        foodComboBox.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        foodComboBox.setBackground(
                Color.WHITE
        );

        foodComboBox.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        orderPanel.add(
                foodComboBox
        );

        orderPanel.add(
                Box.createVerticalStrut(20)
        );


        // QUANTITY

        orderPanel.add(
                createLabel(
                        "Quantity",
                        DARK,
                        14
                )
        );

        orderPanel.add(
                Box.createVerticalStrut(7)
        );

        quantityField =
                new JTextField();

        styleTextField(
                quantityField
        );

        orderPanel.add(
                quantityField
        );

        orderPanel.add(
                Box.createVerticalStrut(25)
        );


        // ORDER BUTTONS

        JPanel orderButtons =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                10,
                                0
                        )
                );

        orderButtons.setOpaque(
                false
        );

        orderButtons.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );


        loadMenuButton =
                new JButton(
                        "LOAD MENU"
                );

        placeOrderButton =
                new JButton(
                        "PLACE ORDER"
                );


        styleButton(
                loadMenuButton,
                BLUE
        );

        styleButton(
                placeOrderButton,
                GREEN
        );


        orderButtons.add(
                loadMenuButton
        );

        orderButtons.add(
                placeOrderButton
        );


        orderPanel.add(
                orderButtons
        );


        // =====================================================
        // RIGHT ACTIVITY PANEL
        // =====================================================

        JPanel activityPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        activityPanel.setBackground(
                Color.WHITE
        );

        activityPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                new Color(
                                        210,
                                        220,
                                        235
                                ),
                                1,
                                true
                        ),
                        new EmptyBorder(
                                18,
                                18,
                                18,
                                18
                        )
                )
        );


        JLabel activityTitle =
                createLabel(
                        "CANTEEN ACTIVITY",
                        NAVY,
                        21
                );


        activityPanel.add(
                activityTitle,
                BorderLayout.NORTH
        );


        // =====================================================
        // COLORFUL OUTPUT AREA
        // =====================================================

        outputArea =
                new JTextPane();

        outputArea.setEditable(
                false
        );

        outputArea.setBackground(
                new Color(
                        248,
                        250,
                        255
                )
        );

        outputArea.setBorder(
                new EmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        outputArea.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        outputArea
                );

        scrollPane.setBorder(
                new LineBorder(
                        new Color(
                                220,
                                225,
                                235
                        ),
                        1
                )
        );


        activityPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        // ADD PANELS

        main.add(
                orderPanel,
                BorderLayout.WEST
        );

        main.add(
                activityPanel,
                BorderLayout.CENTER
        );


        add(
                main,
                BorderLayout.CENTER
        );


        // =====================================================
        // BOTTOM BUTTONS
        // =====================================================

        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                12,
                                12
                        )
                );

        bottom.setBackground(
                BACKGROUND
        );


        viewOrdersButton =
                new JButton(
                        "VIEW ORDERS"
                );

        adminButton =
                new JButton(
                        "ADMIN MENU"
                );

        clearButton =
                new JButton(
                        "CLEAR"
                );

        exitButton =
                new JButton(
                        "EXIT"
                );


        styleButton(
                viewOrdersButton,
                DARK
        );

        styleButton(
                adminButton,
                PURPLE
        );

        styleButton(
                clearButton,
                ORANGE
        );

        styleButton(
                exitButton,
                RED
        );


        bottom.add(
                viewOrdersButton
        );

        bottom.add(
                adminButton
        );

        bottom.add(
                clearButton
        );

        bottom.add(
                exitButton
        );


        add(
                bottom,
                BorderLayout.SOUTH
        );


        // =====================================================
        // EVENT HANDLERS
        // =====================================================

        loadMenuButton.addActionListener(
                e -> loadMenu()
        );

        placeOrderButton.addActionListener(
                e -> placeOrder()
        );

        viewOrdersButton.addActionListener(
                e -> viewOrders()
        );

        adminButton.addActionListener(
                e -> showAdminMenu()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        exitButton.addActionListener(
                e -> confirmExit()
        );
    }


    // =========================================================
    // LABEL STYLE
    // =========================================================

    private JLabel createLabel(
            String text,
            Color color,
            int size
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        size
                )
        );

        label.setForeground(
                color
        );

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return label;
    }


    // =========================================================
    // TEXT FIELD STYLE
    // =========================================================

    private void styleTextField(
            JTextField field
    ) {

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        field.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                new Color(
                                        190,
                                        200,
                                        215
                                ),
                                1
                        ),
                        new EmptyBorder(
                                5,
                                10,
                                5,
                                10
                        )
                )
        );
    }


    // =========================================================
    // BUTTON STYLE
    // =========================================================

    private void styleButton(
            JButton button,
            Color background
    ) {

        button.setBackground(
                background
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        button.setFocusPainted(
                false
        );

        button.setOpaque(
                true
        );

        button.setBorder(
                new EmptyBorder(
                        10,
                        18,
                        10,
                        18
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }


    // =========================================================
    // LOAD MENU
    // =========================================================

    private void loadMenu() {

        foodComboBox.removeAllItems();

        foodList.clear();


        String sql =
                "SELECT food_id, name, price, stock "
                + "FROM food_items "
                + "ORDER BY food_id";


        try (
                Connection con =
                        getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                FoodItem food =
                        new FoodItem(
                                rs.getInt("food_id"),
                                rs.getString("name"),
                                rs.getDouble("price"),
                                rs.getInt("stock")
                        );


                foodList.add(
                        food
                );


                foodComboBox.addItem(
                        food.getName()
                                + " | Rs."
                                + String.format(
                                        "%.2f",
                                        food.getPrice()
                                )
                                + " | Stock: "
                                + food.getStock()
                );
            }


            showMenuOutput();

        } catch (SQLException e) {

            showError(
                    "Unable to load menu:\n\n"
                    + e.getMessage()
            );
        }
    }


    // =========================================================
    // SHOW MENU
    // =========================================================

    private void showMenuOutput() {

        outputArea.setText("");

        StyledDocument doc =
                outputArea.getStyledDocument();


        SimpleAttributeSet heading =
                style(
                        true,
                        21,
                        NAVY
                );


        SimpleAttributeSet foodStyle =
                style(
                        true,
                        15,
                        DARK
                );


        SimpleAttributeSet priceStyle =
                style(
                        true,
                        14,
                        GREEN
                );


        SimpleAttributeSet infoStyle =
                style(
                        false,
                        14,
                        new Color(
                                80,
                                90,
                                105
                        )
                );


        try {

            append(
                    doc,
                    "TODAY'S FOOD MENU\n\n",
                    heading
            );


            append(
                    doc,
                    "────────────────────────────────────────\n",
                    infoStyle
            );


            for (FoodItem food :
                    foodList) {

                append(
                        doc,
                        "\n"
                        + food.getName()
                        + "\n",
                        foodStyle
                );


                append(
                        doc,
                        "   Price : Rs."
                        + String.format(
                                "%.2f",
                                food.getPrice()
                        )
                        + "\n"
                        + "   Stock : "
                        + food.getStock()
                        + "\n",
                        priceStyle
                );


                append(
                        doc,
                        "────────────────────────────────────────\n",
                        infoStyle
                );
            }


            append(
                    doc,
                    "\n✓ Menu loaded successfully!\n\n",
                    priceStyle
            );


            append(
                    doc,
                    "Select an item, enter quantity, "
                    + "and click PLACE ORDER.",
                    infoStyle
            );

        } catch (Exception e) {

            showError(
                    "Unable to display menu: "
                    + e.getMessage()
            );
        }
    }


    // =========================================================
    // TEXT STYLE
    // =========================================================

    private SimpleAttributeSet style(
            boolean bold,
            int size,
            Color color
    ) {

        SimpleAttributeSet set =
                new SimpleAttributeSet();


        StyleConstants.setBold(
                set,
                bold
        );


        StyleConstants.setFontSize(
                set,
                size
        );


        // IMPORTANT:
        // ATTRIBUTE SET + COLOR
        StyleConstants.setForeground(
                set,
                color
        );


        return set;
    }


    // =========================================================
    // APPEND TEXT
    // =========================================================

    private void append(
            StyledDocument doc,
            String text,
            SimpleAttributeSet style
    ) throws Exception {

        doc.insertString(
                doc.getLength(),
                text,
                style
        );
    }


    // =========================================================
    // PLACE ORDER
    // =========================================================

    private void placeOrder() {

        String customer =
                customerField
                        .getText()
                        .trim();


        if (customer.isEmpty()) {

            showError(
                    "Please enter customer name."
            );

            customerField.requestFocus();

            return;
        }


        int quantity;


        try {

            quantity =
                    Integer.parseInt(
                            quantityField
                                    .getText()
                                    .trim()
                    );

        } catch (NumberFormatException e) {

            showError(
                    "Please enter a valid quantity."
            );

            quantityField.requestFocus();

            return;
        }


        try {

            if (quantity <= 0) {

                throw new InvalidOrderException(
                        "Quantity must be greater than zero."
                );
            }


            int index =
                    foodComboBox
                            .getSelectedIndex();


            if (
                    index < 0
                    ||
                    index >= foodList.size()
            ) {

                throw new InvalidOrderException(
                        "Please select a food item."
                );
            }


            FoodItem food =
                    foodList.get(index);


            showProcessing(
                    customer,
                    food,
                    quantity
            );


            OrderThread thread =
                    new OrderThread(
                            this,
                            customer,
                            food,
                            quantity
                    );


            thread.start();


        } catch (InvalidOrderException e) {

            showError(
                    e.getMessage()
            );
        }
    }


    // =========================================================
    // PROCESSING OUTPUT
    // =========================================================

    private void showProcessing(
            String customer,
            FoodItem food,
            int quantity
    ) {

        outputArea.setText("");


        StyledDocument doc =
                outputArea.getStyledDocument();


        SimpleAttributeSet heading =
                style(
                        true,
                        20,
                        BLUE
                );


        SimpleAttributeSet normal =
                style(
                        false,
                        15,
                        DARK
                );


        try {

            append(
                    doc,
                    "PROCESSING YOUR ORDER\n\n",
                    heading
            );


            append(
                    doc,
                    "Customer : "
                    + customer
                    + "\n"
                    + "Food     : "
                    + food.getName()
                    + "\n"
                    + "Quantity : "
                    + quantity
                    + "\n\n"
                    + "Processing in background thread...\n"
                    + "Please wait for the bill.",
                    normal
            );


        } catch (Exception e) {

            showError(
                    e.getMessage()
            );
        }
    }


    // =========================================================
    // SAVE ORDER
    // SYNCHRONIZED FOR MULTITHREADING
    // =========================================================

    public synchronized void saveOrder(
            String customerName,
            FoodItem food,
            int quantity
    ) {

        Connection con = null;


        try {

            con =
                    getConnection();


            con.setAutoCommit(
                    false
            );


            // =================================================
            // CHECK STOCK
            // =================================================

            int currentStock;

            double price;

            String foodName;


            String stockSQL =
                    "SELECT stock, price, name "
                    + "FROM food_items "
                    + "WHERE food_id = ? "
                    + "FOR UPDATE";


            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    stockSQL
                            )
            ) {

                ps.setInt(
                        1,
                        food.getId()
                );


                try (
                        ResultSet rs =
                                ps.executeQuery()
                ) {

                    if (!rs.next()) {

                        throw new InvalidOrderException(
                                "Food item does not exist."
                        );
                    }


                    currentStock =
                            rs.getInt(
                                    "stock"
                            );


                    price =
                            rs.getDouble(
                                    "price"
                            );


                    foodName =
                            rs.getString(
                                    "name"
                            );
                }
            }


            // =================================================
            // STOCK VALIDATION
            // =================================================

            if (quantity > currentStock) {

                throw new InsufficientStockException(
                        "Insufficient stock!\n\n"
                        + "Food: "
                        + foodName
                        + "\n"
                        + "Available stock: "
                        + currentStock
                        + "\n"
                        + "Requested: "
                        + quantity
                );
            }


            // =================================================
            // INSERT CUSTOMER
            // =================================================

            int customerId;


            String customerSQL =
                    "INSERT INTO customers "
                    + "(name, user_type) "
                    + "VALUES (?, ?)";


            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    customerSQL,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                ps.setString(
                        1,
                        customerName
                );


                ps.setString(
                        2,
                        "Student"
                );


                ps.executeUpdate();


                try (
                        ResultSet keys =
                                ps.getGeneratedKeys()
                ) {

                    if (!keys.next()) {

                        throw new SQLException(
                                "Unable to create customer."
                        );
                    }


                    customerId =
                            keys.getInt(1);
                }
            }


            // =================================================
            // CALCULATE TOTAL
            // =================================================

            double total =
                    price * quantity;


            // =================================================
            // INSERT ORDER
            // =================================================

            int orderId;


            String orderSQL =
                    "INSERT INTO orders "
                    + "(customer_id, food_id, quantity, total, status) "
                    + "VALUES (?, ?, ?, ?, ?)";


            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    orderSQL,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                ps.setInt(
                        1,
                        customerId
                );


                ps.setInt(
                        2,
                        food.getId()
                );


                ps.setInt(
                        3,
                        quantity
                );


                ps.setDouble(
                        4,
                        total
                );


                ps.setString(
                        5,
                        "PLACED"
                );


                ps.executeUpdate();


                try (
                        ResultSet keys =
                                ps.getGeneratedKeys()
                ) {

                    if (!keys.next()) {

                        throw new SQLException(
                                "Unable to create order."
                        );
                    }


                    orderId =
                            keys.getInt(1);
                }
            }


            // =================================================
            // UPDATE STOCK
            // =================================================

            String updateSQL =
                    "UPDATE food_items "
                    + "SET stock = stock - ? "
                    + "WHERE food_id = ?";


            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    updateSQL
                            )
            ) {

                ps.setInt(
                        1,
                        quantity
                );


                ps.setInt(
                        2,
                        food.getId()
                );


                ps.executeUpdate();
            }


            // =================================================
            // INSERT BILL
            // =================================================

            String billSQL =
                    "INSERT INTO bills "
                    + "(order_id, amount) "
                    + "VALUES (?, ?)";


            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    billSQL
                            )
            ) {

                ps.setInt(
                        1,
                        orderId
                );


                ps.setDouble(
                        2,
                        total
                );


                ps.executeUpdate();
            }


            // =================================================
            // COMMIT
            // =================================================

            con.commit();


            // =================================================
            // COLLECTION
            // =================================================

            orderList.add(
                    new Order(
                            orderId,
                            customerName,
                            foodName,
                            quantity,
                            total
                    )
            );


            // =================================================
            // SHOW BILL
            // =================================================

            final int finalOrderId =
                    orderId;

            final double finalPrice =
                    price;

            final String finalFoodName =
                    foodName;

            SwingUtilities.invokeLater(
                    () -> {

                        showBill(
                                finalOrderId,
                                customerName,
                                finalFoodName,
                                quantity,
                                finalPrice,
                                total
                        );


                        loadMenu();
                    }
            );


        } catch (
                InvalidOrderException
                |
                InsufficientStockException e
        ) {

            rollback(
                    con
            );


            SwingUtilities.invokeLater(
                    () ->
                            showError(
                                    e.getMessage()
                            )
            );


        } catch (SQLException e) {

            rollback(
                    con
            );


            SwingUtilities.invokeLater(
                    () ->
                            showError(
                                    "Database Error:\n\n"
                                    + e.getMessage()
                            )
            );


        } finally {

            closeConnection(
                    con
            );
        }
    }


    // =========================================================
    // SHOW BILL
    // =========================================================

    private void showBill(
            int orderId,
            String customer,
            String food,
            int quantity,
            double price,
            double total
    ) {

        outputArea.setText("");


        StyledDocument doc =
                outputArea.getStyledDocument();


        SimpleAttributeSet heading =
                style(
                        true,
                        22,
                        GREEN
                );


        SimpleAttributeSet normal =
                style(
                        false,
                        15,
                        DARK
                );


        SimpleAttributeSet totalStyle =
                style(
                        true,
                        19,
                        GREEN
                );


        try {

            append(
                    doc,
                    "✓ ORDER PLACED SUCCESSFULLY!\n\n",
                    heading
            );


            append(
                    doc,
                    "Order ID     : "
                    + orderId
                    + "\n\n"
                    + "Customer     : "
                    + customer
                    + "\n"
                    + "Food Item    : "
                    + food
                    + "\n"
                    + "Quantity     : "
                    + quantity
                    + "\n"
                    + "Price        : Rs."
                    + String.format(
                            "%.2f",
                            price
                    )
                    + "\n\n"
                    + "────────────────────────────────────────\n",
                    normal
            );


            append(
                    doc,
                    "TOTAL BILL   : Rs."
                    + String.format(
                            "%.2f",
                            total
                    )
                    + "\n",
                    totalStyle
            );


            append(
                    doc,
                    "────────────────────────────────────────\n\n"
                    + "Status       : ORDER PLACED\n\n"
                    + "Thank you for ordering!",
                    normal
            );


        } catch (Exception e) {

            showError(
                    e.getMessage()
            );
        }
    }


    // =========================================================
    // VIEW ORDERS
    // =========================================================

    private void viewOrders() {

        String sql =
                "SELECT "
                + "o.order_id, "
                + "c.name AS customer_name, "
                + "f.name AS food_name, "
                + "o.quantity, "
                + "o.total, "
                + "o.status "
                + "FROM orders o "
                + "JOIN customers c "
                + "ON o.customer_id = c.customer_id "
                + "JOIN food_items f "
                + "ON o.food_id = f.food_id "
                + "ORDER BY o.order_id DESC";


        StringBuilder sb =
                new StringBuilder();


        try (
                Connection con =
                        getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            boolean found =
                    false;


            while (rs.next()) {

                found =
                        true;


                sb.append(
                        "Order ID : "
                );

                sb.append(
                        rs.getInt(
                                "order_id"
                        )
                );

                sb.append(
                        "\n"
                );


                sb.append(
                        "Customer : "
                );

                sb.append(
                        rs.getString(
                                "customer_name"
                        )
                );

                sb.append(
                        "\n"
                );


                sb.append(
                        "Food     : "
                );

                sb.append(
                        rs.getString(
                                "food_name"
                        )
                );

                sb.append(
                        "\n"
                );


                sb.append(
                        "Quantity : "
                );

                sb.append(
                        rs.getInt(
                                "quantity"
                        )
                );

                sb.append(
                        "\n"
                );


                sb.append(
                        "Total    : Rs."
                );

                sb.append(
                        String.format(
                                "%.2f",
                                rs.getDouble(
                                        "total"
                                )
                        )
                );

                sb.append(
                        "\n"
                );


                sb.append(
                        "Status   : "
                );

                sb.append(
                        rs.getString(
                                "status"
                        )
                );

                sb.append(
                        "\n"
                );


                sb.append(
                        "────────────────────────────────────────\n\n"
                );
            }


            if (!found) {

                sb.append(
                        "No orders found."
                );
            }


            showOrdersOutput(
                    sb.toString()
            );


        } catch (SQLException e) {

            showError(
                    "Database Error:\n\n"
                    + e.getMessage()
            );
        }
    }


    // =========================================================
    // SHOW ORDERS
    // =========================================================

    private void showOrdersOutput(
            String text
    ) {

        outputArea.setText("");


        StyledDocument doc =
                outputArea.getStyledDocument();


        SimpleAttributeSet heading =
                style(
                        true,
                        21,
                        NAVY
                );


        SimpleAttributeSet normal =
                style(
                        false,
                        14,
                        DARK
                );


        try {

            append(
                    doc,
                    "ORDER HISTORY\n\n",
                    heading
            );


            append(
                    doc,
                    text,
                    normal
            );


        } catch (Exception e) {

            showError(
                    e.getMessage()
            );
        }
    }


    // =========================================================
    // ADMIN MENU
    // =========================================================

    private void showAdminMenu() {

        String[] options = {

                "ADD FOOD",

                "UPDATE STOCK",

                "DELETE FOOD",

                "VIEW MENU",

                "CANCEL"
        };


        int choice =
                JOptionPane.showOptionDialog(

                        this,

                        "Select an administration operation:",

                        "ADMIN MENU",

                        JOptionPane.DEFAULT_OPTION,

                        JOptionPane.INFORMATION_MESSAGE,

                        null,

                        options,

                        options[0]
                );


        switch (choice) {

            case 0:

                addFood();

                break;


            case 1:

                updateStock();

                break;


            case 2:

                deleteFood();

                break;


            case 3:

                loadMenu();

                break;


            default:

                break;
        }
    }


    // =========================================================
    // ADD FOOD
    // INSERT OPERATION
    // =========================================================

    private void addFood() {

        JTextField nameField =
                new JTextField();

        JTextField priceField =
                new JTextField();

        JTextField stockField =
                new JTextField();


        Object[] fields = {

                "Food Name:",
                nameField,

                "Price:",
                priceField,

                "Stock:",
                stockField
        };


        int result =
                JOptionPane.showConfirmDialog(

                        this,

                        fields,

                        "ADD FOOD",

                        JOptionPane.OK_CANCEL_OPTION,

                        JOptionPane.PLAIN_MESSAGE
                );


        if (
                result
                !=
                JOptionPane.OK_OPTION
        ) {

            return;
        }


        try {

            String name =
                    nameField
                            .getText()
                            .trim();


            double price =
                    Double.parseDouble(
                            priceField
                                    .getText()
                                    .trim()
                    );


            int stock =
                    Integer.parseInt(
                            stockField
                                    .getText()
                                    .trim()
                    );


            if (name.isEmpty()) {

                throw new InvalidOrderException(
                        "Food name cannot be empty."
                );
            }


            if (price <= 0) {

                throw new InvalidOrderException(
                        "Price must be greater than zero."
                );
            }


            if (stock < 0) {

                throw new InvalidOrderException(
                        "Stock cannot be negative."
                );
            }


            String sql =
                    "INSERT INTO food_items "
                    + "(name, price, stock) "
                    + "VALUES (?, ?, ?)";


            try (
                    Connection con =
                            getConnection();

                    PreparedStatement ps =
                            con.prepareStatement(sql)
            ) {

                ps.setString(
                        1,
                        name
                );


                ps.setDouble(
                        2,
                        price
                );


                ps.setInt(
                        3,
                        stock
                );


                ps.executeUpdate();
            }


            JOptionPane.showMessageDialog(

                    this,

                    "Food added successfully!",

                    "SUCCESS",

                    JOptionPane.INFORMATION_MESSAGE
            );


            loadMenu();


        } catch (NumberFormatException e) {

            showError(
                    "Enter valid price and stock."
            );


        } catch (InvalidOrderException e) {

            showError(
                    e.getMessage()
            );


        } catch (SQLException e) {

            showError(
                    "Database Error:\n\n"
                    + e.getMessage()
            );
        }
    }


    // =========================================================
    // UPDATE STOCK
    // UPDATE OPERATION
    // =========================================================

    private void updateStock() {

        String idText =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Food ID:"
                );


        if (idText == null) {

            return;
        }


        String stockText =
                JOptionPane.showInputDialog(
                        this,
                        "Enter New Stock:"
                );


        if (stockText == null) {

            return;
        }


        try {

            int id =
                    Integer.parseInt(
                            idText.trim()
                    );


            int stock =
                    Integer.parseInt(
                            stockText.trim()
                    );


            if (stock < 0) {

                throw new InvalidOrderException(
                        "Stock cannot be negative."
                );
            }


            String sql =
                    "UPDATE food_items "
                    + "SET stock = ? "
                    + "WHERE food_id = ?";


            try (
                    Connection con =
                            getConnection();

                    PreparedStatement ps =
                            con.prepareStatement(sql)
            ) {

                ps.setInt(
                        1,
                        stock
                );


                ps.setInt(
                        2,
                        id
                );


                int rows =
                        ps.executeUpdate();


                if (rows == 0) {

                    throw new SQLException(
                            "Food ID not found."
                    );
                }
            }


            JOptionPane.showMessageDialog(

                    this,

                    "Stock updated successfully!",

                    "SUCCESS",

                    JOptionPane.INFORMATION_MESSAGE
            );


            loadMenu();


        } catch (NumberFormatException e) {

            showError(
                    "Enter valid numbers."
            );


        } catch (InvalidOrderException e) {

            showError(
                    e.getMessage()
            );


        } catch (SQLException e) {

            showError(
                    "Database Error:\n\n"
                    + e.getMessage()
            );
        }
    }


    // =========================================================
    // DELETE FOOD
    // DELETE OPERATION
    // =========================================================

    private void deleteFood() {

        String idText =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Food ID to delete:"
                );


        if (idText == null) {

            return;
        }


        try {

            int id =
                    Integer.parseInt(
                            idText.trim()
                    );


            int confirm =
                    JOptionPane.showConfirmDialog(

                            this,

                            "Are you sure you want to delete Food ID "
                            + id
                            + "?",

                            "CONFIRM DELETE",

                            JOptionPane.YES_NO_OPTION,

                            JOptionPane.WARNING_MESSAGE
                    );


            if (
                    confirm
                    !=
                    JOptionPane.YES_OPTION
            ) {

                return;
            }


            String sql =
                    "DELETE FROM food_items "
                    + "WHERE food_id = ?";


            try (
                    Connection con =
                            getConnection();

                    PreparedStatement ps =
                            con.prepareStatement(sql)
            ) {

                ps.setInt(
                        1,
                        id
                );


                int rows =
                        ps.executeUpdate();


                if (rows == 0) {

                    throw new SQLException(
                            "Food ID not found."
                    );
                }
            }


            JOptionPane.showMessageDialog(

                    this,

                    "Food deleted successfully!",

                    "SUCCESS",

                    JOptionPane.INFORMATION_MESSAGE
            );


            loadMenu();


        } catch (NumberFormatException e) {

            showError(
                    "Enter a valid Food ID."
            );


        } catch (SQLException e) {

            showError(
                    "Database Error:\n\n"
                    + e.getMessage()
            );
        }
    }


    // =========================================================
    // CLEAR
    // =========================================================

    private void clearFields() {

        customerField.setText("");

        quantityField.setText("");


        if (
                foodComboBox.getItemCount()
                > 0
        ) {

            foodComboBox.setSelectedIndex(
                    0
            );
        }


        showReadyOutput();


        customerField.requestFocus();
    }


    // =========================================================
    // READY OUTPUT
    // =========================================================

    private void showReadyOutput() {

        outputArea.setText("");


        StyledDocument doc =
                outputArea.getStyledDocument();


        SimpleAttributeSet heading =
                style(
                        true,
                        21,
                        NAVY
                );


        SimpleAttributeSet normal =
                style(
                        false,
                        15,
                        DARK
                );


        try {

            append(
                    doc,
                    "READY TO TAKE YOUR ORDER\n\n",
                    heading
            );


            append(
                    doc,
                    "1. Enter customer name\n"
                    + "2. Select a food item\n"
                    + "3. Enter quantity\n"
                    + "4. Click PLACE ORDER\n\n"
                    + "Your bill will appear here.",
                    normal
            );


        } catch (Exception e) {

            showError(
                    e.getMessage()
            );
        }
    }


    // =========================================================
    // ROLLBACK
    // =========================================================

    private void rollback(
            Connection con
    ) {

        if (con != null) {

            try {

                con.rollback();

            } catch (SQLException ignored) {

            }
        }
    }


    // =========================================================
    // CLOSE CONNECTION
    // =========================================================

    private void closeConnection(
            Connection con
    ) {

        if (con != null) {

            try {

                con.close();

            } catch (SQLException ignored) {

            }
        }
    }


    // =========================================================
    // ERROR MESSAGE
    // =========================================================

    private void showError(
            String message
    ) {

        JOptionPane.showMessageDialog(

                this,

                message,

                "ERROR",

                JOptionPane.ERROR_MESSAGE
        );
    }


    // =========================================================
    // EXIT
    // =========================================================

    private void confirmExit() {

        int result =
                JOptionPane.showConfirmDialog(

                        this,

                        "Are you sure you want to exit?",

                        "EXIT APPLICATION",

                        JOptionPane.YES_NO_OPTION,

                        JOptionPane.QUESTION_MESSAGE
                );


        if (
                result
                ==
                JOptionPane.YES_OPTION
        ) {

            System.exit(0);
        }
    }


    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    CanteenManagement application =
                            new CanteenManagement();

                    application.setVisible(
                            true
                    );
                }
        );
    }
}


// =============================================================
// FOOD ITEM CLASS
// =============================================================

class FoodItem {

    private final int id;

    private final String name;

    private final double price;

    private final int stock;


    public FoodItem(
            int id,
            String name,
            double price,
            int stock
    ) {

        this.id = id;

        this.name = name;

        this.price = price;

        this.stock = stock;
    }


    public int getId() {

        return id;
    }


    public String getName() {

        return name;
    }


    public double getPrice() {

        return price;
    }


    public int getStock() {

        return stock;
    }
}


// =============================================================
// ORDER CLASS
// =============================================================

class Order {

    private final int orderId;

    private final String customerName;

    private final String foodName;

    private final int quantity;

    private final double total;


    public Order(
            int orderId,
            String customerName,
            String foodName,
            int quantity,
            double total
    ) {

        this.orderId = orderId;

        this.customerName = customerName;

        this.foodName = foodName;

        this.quantity = quantity;

        this.total = total;
    }


    public int getOrderId() {

        return orderId;
    }


    public String getCustomerName() {

        return customerName;
    }


    public String getFoodName() {

        return foodName;
    }


    public int getQuantity() {

        return quantity;
    }


    public double getTotal() {

        return total;
    }
}


// =============================================================
// ORDER THREAD
// =============================================================

class OrderThread extends Thread {

    private final CanteenManagement application;

    private final String customerName;

    private final FoodItem food;

    private final int quantity;


    public OrderThread(
            CanteenManagement application,
            String customerName,
            FoodItem food,
            int quantity
    ) {

        this.application =
                application;

        this.customerName =
                customerName;

        this.food =
                food;

        this.quantity =
                quantity;
    }


    @Override
    public void run() {

        application.saveOrder(
                customerName,
                food,
                quantity
        );
    }
}


// =============================================================
// INVALID ORDER EXCEPTION
// =============================================================

class InvalidOrderException
        extends Exception {

    public InvalidOrderException(
            String message
    ) {

        super(message);
    }
}


// =============================================================
// INSUFFICIENT STOCK EXCEPTION
// =============================================================

class InsufficientStockException
        extends Exception {

    public InsufficientStockException(
            String message
    ) {

        super(message);
    }
}