package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.sql.ResultSet;

public class FastCash extends JFrame implements ActionListener {

    JButton deposit, withdrawal, miniStatement, pinChange, fastCash, balanceEnquiry, exit;
    String pinNumber;

    FastCash(String pinNumber){

        this.pinNumber = pinNumber;
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 900);
        add(image);

        JLabel text = new JLabel("Select Withdrawal Amount");
        text.setBounds(215, 300, 700, 35);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System", Font.BOLD, 16));
        image.add(text);

        deposit = new JButton("Rs 1000");
        deposit.setBounds(170, 415, 150, 30);
        deposit.addActionListener(this);
        image.add(deposit);

        withdrawal = new JButton("Rs 2000");
        withdrawal.setBounds(355, 415, 150, 30);
        withdrawal.addActionListener(this);
        image.add(withdrawal);

        fastCash = new JButton("Rs 5000");
        fastCash.setBounds(170, 450, 150, 30);
        fastCash.addActionListener(this);
        image.add(fastCash);

        miniStatement = new JButton("Rs 10000");
        miniStatement.setBounds(355, 450, 150, 30);
        miniStatement.addActionListener(this);
        image.add(miniStatement);

        pinChange = new JButton("Rs 15000");
        pinChange.setBounds(170, 485, 150, 30);
        pinChange.addActionListener(this);
        image.add(pinChange);

        balanceEnquiry = new JButton("Rs 20000");
        balanceEnquiry.setBounds(355, 485, 150, 30);
        balanceEnquiry.addActionListener(this);
        image.add(balanceEnquiry);

        exit = new JButton("Back");
        exit.setBounds(355, 520, 150, 30);
        exit.addActionListener(this);
        image.add(exit);

        setSize(900, 900);
        setLocation(300, 0);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == exit){
            setVisible(false);
            new Transactions(pinNumber).setVisible(true);
        } else {
            String amount = ((JButton)ae.getSource()).getText().substring(3); // Rs 1000
            Conn c = new Conn();
            try {
                ResultSet rs = c.s.executeQuery("select * from bank where pin = '"+pinNumber+"'");
                int balance = 0;
                while (rs.next()){
                    if (rs.getString("type").equals("Deposit")){
                        balance += Integer.parseInt(rs.getString("amount"));
                    }else {
                        balance -= Integer.parseInt(rs.getString("amount"));
                    }
                }
                if (ae.getSource() != exit && balance < Integer.parseInt(amount)){
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                }

                Date date = new Date();
                String query = "insert into bank values('"+pinNumber+"', '"+date+"', 'Withdrawal', '"+amount+"')";
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Rs " + amount + " Debited Successfully");

                setVisible(false);
                new Transactions(pinNumber).setVisible(true);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) {

        new FastCash("");
    }
}
