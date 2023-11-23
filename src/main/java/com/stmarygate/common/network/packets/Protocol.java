package com.stmarygate.common.network.packets;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Protocol {
    private static final HashMap<Integer, Class<? extends Packet>> PACKETS_MAP = new HashMap<>();
    private static final Protocol INSTANCE = new Protocol();

    public Protocol() {

    }

    private void register(int id, Class<? extends Packet> packet) {
        try {
            packet.getDeclaredConstructor().newInstance();
            PACKETS_MAP.put(id, packet);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException("Class " + packet.getSimpleName() + " does not contain a default Constructor!");
        }
    }

    public int getPacketId(Packet packet) {
        Optional<Integer> id = PACKETS_MAP.entrySet().stream().filter(entry -> entry.getValue().isInstance(packet)).map(Map.Entry::getKey).findFirst();
        if (id.isPresent())
            return id.get();

        throw new RuntimeException("Packet " + packet + " is not registered.");
    }

    public Packet getPacket(int id) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (!PACKETS_MAP.containsKey(id))
            throw new RuntimeException("Packet with id " + id + " is not registered.");
        return PACKETS_MAP.get(id).getDeclaredConstructor().newInstance();
    }

    public boolean hasPacket(int id) {
        return PACKETS_MAP.containsKey(id);
    }

    public static Protocol getInstance() {
        return INSTANCE;
    }
}
