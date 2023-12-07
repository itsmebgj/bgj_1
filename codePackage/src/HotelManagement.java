import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class HotelManagement extends JFrame {

    private List<Room> rooms;

    public HotelManagement() {
        super("Hotel Management System");
        rooms = generateRoomData(); // 방 데이터 생성

        // "Current Date" 버튼 생성 및 리스너 추가
        JButton dateButton = new JButton("Current Date");
        dateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRoomStatus(); // 날짜 버튼 클릭 시 방 상태 업데이트
            }
        });

        // 방 버튼을 표시할 패널 생성
        JPanel roomPanel = new JPanel(new GridLayout(4, 5, 10, 10));
        for (Room room : rooms) {
            JButton roomButton = new JButton(room.getRoomNumber());
            roomButton.setBackground(room.getStatusColor());
            roomButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showRoomDetails(room); // 방 버튼 클릭 시 상세 정보 표시
                }
            });
            roomPanel.add(roomButton);
        }

        // 메인 패널 생성 및 구성
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(dateButton, BorderLayout.NORTH);
        mainPanel.add(roomPanel, BorderLayout.CENTER);

        // JFrame에 메인 패널 추가
        getContentPane().add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 가상의 방 데이터 생성
    private List<Room> generateRoomData() {
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            rooms.add(new Room());
        }
        return rooms;
    }

    // "Current Date" 버튼 클릭 시 호출되는 메서드
    private void updateRoomStatus() {
        for (Room room : rooms) {
            room.setRandomStatus(); // 랜덤으로 방 상태 업데이트
        }
        refreshUI(); // UI 갱신
    }

    // 방 버튼 클릭 시 호출되는 메서드
    private void showRoomDetails(Room room) {
        // 방 상세 정보 다이얼로그 표시
        JOptionPane.showMessageDialog(this, "Room: " + room.getRoomNumber() + "\nStatus: " + room.getStatus());
    }

    // UI 갱신 메서드
    private void refreshUI() {
        getContentPane().removeAll(); // 모든 컴포넌트 제거
        revalidate(); // 레이아웃 다시 계산
        repaint(); // 다시 그리기

        // 방 버튼을 표시할 패널 재생성
        JPanel roomPanel = new JPanel(new GridLayout(4, 5, 10, 10));
        for (Room room : rooms) {
            JButton roomButton = new JButton(room.getRoomNumber());
            roomButton.setBackground(room.getStatusColor());
            roomButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showRoomDetails(room);
                }
            });
            roomPanel.add(roomButton);
        }

        // 메인 패널 재생성
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JButton("Current Date"), BorderLayout.NORTH);
        mainPanel.add(roomPanel, BorderLayout.CENTER);

        // JFrame에 메인 패널 추가
        getContentPane().add(mainPanel);
        revalidate(); // 레이아웃 다시 계산
        repaint(); // 다시 그리기
    }

    // 방 클래스 정의
    private static class Room {
        private String roomNumber;
        private Color statusColor;

        public Room() {
            this.roomNumber = "Room " + roomNumber;
            this.statusColor = Color.GREEN; // 초록색 (기본값: 공실)
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        public Color getStatusColor() {
            return statusColor;
        }

        public String getStatus() {
            if (statusColor.equals(Color.GREEN)) {
                return "Available";
            } else if (statusColor.equals(Color.BLUE)) {
                return "Reserved";
            } else if (statusColor.equals(Color.RED)) {
                return "Occupied";
            }
            return "";
        }

        public void setRandomStatus() {
            int random = (int) (Math.random() * 3);
            switch (random) {
                case 0:
                    this.statusColor = Color.GREEN; // 초록색 (공실)
                    break;
                case 1:
                    this.statusColor = Color.BLUE; // 파랑색 (예약 중)
                    break;
                case 2:
                    this.statusColor = Color.RED; // 빨강색 (투숙 중)
                    break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HotelManagement();
            }
        });
    }
}
