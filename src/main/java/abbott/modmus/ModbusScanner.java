package abbott.modmus;
import java.net.InetAddress;

import com.ghgande.j2mod.modbus.io.ModbusTCPTransaction;
import com.ghgande.j2mod.modbus.msg.ReadInputRegistersRequest;
import com.ghgande.j2mod.modbus.msg.ReadInputRegistersResponse;
import com.ghgande.j2mod.modbus.net.TCPMasterConnection;

public class ModbusScanner {
    public static void main(String[] args) {
        try {
            // IP-адрес и порт Modbus TCP slave-устройства
            TCPMasterConnection connection = new TCPMasterConnection(InetAddress.getByName(Data.IP_ADDRESS));
            connection.setPort(Data.PORT);
            connection.connect();

            // Создание объектов запроса и ответа
            ReadInputRegistersRequest request = new ReadInputRegistersRequest(0, 10);
            ReadInputRegistersResponse response = new ReadInputRegistersResponse();

            // Создание транзакции и отправка запроса
            ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);
            transaction.setRequest(request);
            transaction.execute();

            // Получение и обработка ответа
            if (transaction.getResponse() instanceof ReadInputRegistersResponse) {
                response = (ReadInputRegistersResponse) transaction.getResponse();
                System.out.println("Данные: " + response.getRegisterValue(0));
            } else {
                System.out.println("Произошла ошибка: " + transaction.getResponse().getHexMessage());
            }

            // Закрытие соединения
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
