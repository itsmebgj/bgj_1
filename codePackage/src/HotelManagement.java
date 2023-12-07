import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HotelManagement extends JFrame {

    private List<Room> rooms;
    private JButton dateButton; // 날짜 버튼
    private int currentDateIndex = 0; // 현재 표시 중인 날짜의 인덱스
    private List<String> recentDates; // 최근 5일의 날짜 목록

    public HotelManagement() {
        super("Hotel Management System");
        rooms = generateRoomData(); // 방 데이터 생성
        recentDates = generateRecentDates(); // 최근 5일의 날짜 목록 생성

        // "Current Date" 버튼 생성 및 리스너 추가
        dateButton = new JButton(recentDates.get(currentDateIndex));
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

        // 타이머를 사용하여 주기적으로 날짜 업데이트
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateDate();
            }
        }, 0, 1000); // 1초마다 업데이트
    }

    // 현재 날짜를 기준으로 최근 5일의 날짜를 생성
    private List<String> generateRecentDates() {
        List<String> dates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 4; i >= 0; i--) {
            dates.add(dateFormat.format(new Date(System.currentTimeMillis() - i * 24 * 60 * 60 * 1000)));
        }

        return dates;
    }

    // 날짜 업데이트 메서드
    private void updateDate() {
        currentDateIndex = (currentDateIndex + 1) % recentDates.size(); // 다음 날짜로 업데이트
        dateButton.setText(recentDates.get(currentDateIndex)); // 버튼에 새로운 날짜 설정
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
        mainPanel.add(dateButton, BorderLayout.NORTH);
        mainPanel.add(roomPanel, BorderLayout.CENTER);

        // JFrame에 메인 패널 추가
        getContentPane().add(mainPanel);
        revalidate(); // 레이아웃 다시 계산
        repaint(); // 다시 그리기
    }
    // 가상의 방 데이터 생성
    private List<Room> generateRoomData() {
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            rooms.add(new Room(i));
        }
        return rooms;
    }
    // 방 클래스 정의
    private static class Room {
        private String roomNumber;
        private Color statusColor;

        public Room(int roomIndex) {
            this.roomNumber = "Room " + (roomIndex + 1); // 인덱스가 1부터 시작하도록 조정
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