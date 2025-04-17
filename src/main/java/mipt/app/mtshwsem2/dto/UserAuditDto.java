package mipt.app.mtshwsem2.dto;

import java.time.Instant;
import java.util.UUID;

public record UserAuditDto(UUID userId, Instant time, String action, String eventDetails) {}
