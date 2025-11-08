package ui;

import dao.StudentDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.Student;

public class StudentForm extends JFrame {

    private JTextField tfName = new JTextField();
    private JTextField tfRoll = new JTextField();
    private JTextField tfCourse = new JTextField();
    private JTextField tfYear = new JTextField();
    private JTextField tfPhone = new JTextField();
    private JTextField tfRoom = new JTextField();
    private JTextField tfSearch = new JTextField();

    private DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID","Name","Roll No","Course","Year","Phone","Room"},0
    );
    private JTable table = new JTable(model);
    private int selectedId = -1;

    public StudentForm() {

        setTitle("Hostel Management System");
        setSize(950,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Heading
        JLabel title = new JLabel("Hostel Management System", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBorder(new EmptyBorder(10,10,10,10));
        add(title, BorderLayout.NORTH);

        // Left Form
        JPanel left = new JPanel(new GridLayout(8,1,6,6));
        left.setBorder(new EmptyBorder(15,15,15,15));
        left.setBackground(Color.WHITE);

        left.add(formField("Name:", tfName));
        left.add(formField("Roll No:", tfRoll));
        left.add(formField("Course:", tfCourse));
        left.add(formField("Year:", tfYear));
        left.add(formField("Phone:", tfPhone));
        left.add(formField("Room No:", tfRoom));

        JPanel btnPanel = new JPanel(new GridLayout(1,4,8,8));
        btnPanel.setBackground(Color.WHITE);

        JButton btnAdd = button("Add");
        JButton btnUpdate = button("Update");
        JButton btnDelete = button("Delete");
        JButton btnClear = button("Clear");

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        left.add(btnPanel);
        add(left, BorderLayout.WEST);

        // Right Panel
        JPanel right = new JPanel(new BorderLayout(8,8));
        right.setBorder(new EmptyBorder(10,10,10,10));

        JPanel topSearch = new JPanel(new BorderLayout(6,6));
        topSearch.add(new JLabel("Search:"), BorderLayout.WEST);
        topSearch.add(tfSearch, BorderLayout.CENTER);

        JButton btnRefresh = button("Refresh");
        topSearch.add(btnRefresh, BorderLayout.EAST);
        right.add(topSearch, BorderLayout.NORTH);

        table.setRowHeight(24);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        right.add(new JScrollPane(table), BorderLayout.CENTER);
        add(right, BorderLayout.CENTER);

        // Actions
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearFields());
        btnRefresh.addActionListener(e -> loadTable());
        table.getSelectionModel().addListSelectionListener(e -> fillForm());

        tfSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { search(); }
            public void removeUpdate(DocumentEvent e) { search(); }
            public void changedUpdate(DocumentEvent e) { search(); }
        });

        loadTable();
        setVisible(true);
    }

    private JPanel formField(String label, JTextField tf){
        JPanel p = new JPanel(new BorderLayout(6,6));
        p.setBackground(Color.WHITE);
        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        p.add(l, BorderLayout.WEST);
        p.add(tf, BorderLayout.CENTER);
        return p;
    }

    private JButton button(String text){
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }

    private void addStudent(){
        Student s = new Student(tfName.getText(), tfRoll.getText(), tfCourse.getText(),
                tfYear.getText(), tfPhone.getText(), tfRoom.getText());

        if(StudentDAO.save(s)){
            JOptionPane.showMessageDialog(this,"✅ Student Added Successfully");
            loadTable();
            clearFields();
        }
    }

    private void updateStudent(){
        if(selectedId == -1) return;
        Student s = new Student(selectedId, tfName.getText(), tfRoll.getText(), tfCourse.getText(),
                tfYear.getText(), tfPhone.getText(), tfRoom.getText());

        if(StudentDAO.update(s)){
            JOptionPane.showMessageDialog(this,"✅ Updated Successfully");
            loadTable();
            clearFields();
        }
    }

    private void deleteStudent(){
        if(selectedId == -1) return;
        if(StudentDAO.delete(selectedId)){
            JOptionPane.showMessageDialog(this,"🗑 Deleted Successfully");
            loadTable();
            clearFields();
        }
    }

    private void clearFields(){
        selectedId = -1;
        tfName.setText(""); tfRoll.setText(""); tfCourse.setText(""); tfYear.setText("");
        tfPhone.setText(""); tfRoom.setText("");
    }

    private void loadTable(){
        model.setRowCount(0);
        List<Student> list = StudentDAO.getAll();
        for(Student s : list){
            model.addRow(new Object[]{
                    s.getId(), s.getName(), s.getRollno(), s.getCourse(), s.getYear(), s.getPhone(), s.getRoom()
            });
        }
    }

    private void fillForm(){
        int r = table.getSelectedRow();
        if(r==-1) return;

        selectedId = (int) model.getValueAt(r,0);
        tfName.setText((String) model.getValueAt(r,1));
        tfRoll.setText((String) model.getValueAt(r,2));
        tfCourse.setText((String) model.getValueAt(r,3));
        tfYear.setText((String) model.getValueAt(r,4));
        tfPhone.setText((String) model.getValueAt(r,5));
        tfRoom.setText((String) model.getValueAt(r,6));
    }

    private void search(){
        String key = tfSearch.getText().trim().toLowerCase();
        model.setRowCount(0);

        for(Student s : StudentDAO.getAll()){
            if(s.getName().toLowerCase().contains(key) ||
               s.getRollno().toLowerCase().contains(key) ||
               s.getRoom().toLowerCase().contains(key)){

                model.addRow(new Object[]{
                        s.getId(), s.getName(), s.getRollno(), s.getCourse(),
                        s.getYear(), s.getPhone(), s.getRoom()
                });
            }
        }
    }

    public static void main(String[] args) {
        new StudentForm();
    }
}
