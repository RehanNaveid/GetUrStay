import React, { useEffect, useState } from "react";

const RoomDetails = ({ roomId }) => {
  const [roomData, setRoomData] = useState(null);

  useEffect(() => {
    const fetchRoomDetails = async () => {
      try {
        const res = await fetch(`http://localhost:8080/rooms/${roomId}`);
        if (!res.ok) throw new Error("Room not found");
        const data = await res.json();
        setRoomData(data);
      } catch (error) {
        console.error("Error fetching the room", error);
      }
    };
    fetchRoomDetails();
  }, [roomId]);

  if (!roomData) {
    return <p>Loading room details...</p>;
  }

  // Create sample amenity icons mapping
  const amenityIcons = {
    parking: "P",
    ac: "❄️",
    wifi: "📶",
    refrigerator: "🧊",
    powerBackup: "⚡",
    almirah: "🗄️",
    bedSheet: "🛏️", 
    cctv: "📹",
    houseKeeping: "🧹",
    security: "🔒",
    pillow: "🛌",
    lift: "🔼",
    drinkingWater: "💧",
    bathroom: "🚿",
    wash: "🧼"
  };

  return (
    <div style={styles.container}>
      <div style={styles.section}>
        <h2 style={styles.sectionTitle}>DESCRIPTION</h2>
        <p style={styles.foodNote}>
        This practical and conveniently located private room offers an excellent rental opportunity. Situated in a [mention a positive location aspect, e.g., vibrant neighborhood, quiet residential area, close proximity to public transport], this room provides easy access to [mention key nearby amenities, e.g., local shops, universities, or business districts]. The room itself is equipped with [mention essential features, e.g., a bed, a dedicated study area, and storage solutions]. Enjoy the balance of private space and access to shared facilities. This rental offers a comfortable and functional living arrangement in a desirable location.
        </p>
        <p style={styles.foodNote}>
        Embrace a welcoming and community-oriented living experience in this private room rental. This cozy space offers more than just a room; it provides an opportunity to connect with friendly housemates in shared common areas. The room is furnished with [mention basic amenities, e.g., a comfortable bed and essential storage]. You'll appreciate the [mention a positive shared space feature, e.g., communal kitchen with modern appliances or a comfortable living area for socializing]. Located in a [mention a positive community aspect, e.g., friendly neighborhood with local cafes and parks], this rental offers a comfortable private space within a supportive living environment.
        </p>
      </div>

      <div style={styles.section}>
        <h2 style={styles.sectionTitle}>
          <span style={styles.amazing}>Amazing</span> Amenities
        </h2>
        <div style={styles.amenitiesGrid}>
          {roomData.amenities ? (
            roomData.amenities.map((amenity, index) => (
              <div key={index} style={styles.amenityItem}>
                <div style={styles.amenityIcon}>
                  {amenityIcons[amenity.toLowerCase().replace(/\s+/g, "")] || "✓"}
                </div>
                <span style={styles.amenityName}>{amenity}</span>
              </div>
            ))
          ) : (
            // Sample amenities if none provided by API
            Object.entries(amenityIcons).map(([key, icon], index) => (
              <div key={index} style={styles.amenityItem}>
                <div style={styles.amenityIcon}>{icon}</div>
                <span style={styles.amenityName}>
                  {key.replace(/([A-Z])/g, ' $1').replace(/^./, str => str.toUpperCase())}
                </span>
              </div>
            ))
          )}
        </div>
      </div>

      <div style={styles.section}>
        <h2 style={styles.sectionTitle}>Room Details</h2>
        <p>{roomData.description || "No description available."}</p>
        
        <div style={styles.detailsGrid}>
          <div style={styles.detailItem}>
            <span style={styles.detailLabel}>Room Type:</span>
            <span style={styles.detailValue}>{roomData.roomtype || "N/A"}</span>
          </div>
          <div style={styles.detailItem}>
            <span style={styles.detailLabel}>Max Occupancy:</span>
            <span style={styles.detailValue}>{roomData.maxoccupancy || "N/A"}</span>
          </div>
          <div style={styles.detailItem}>
            <span style={styles.detailLabel}>AC:</span>
            <span style={styles.detailValue}>{roomData.isac ? "Yes" : "No"}</span>
          </div>
        </div>
      </div>
    </div>
  );
};

const styles = {
  container: {
    backgroundColor: "#ffffff",
    borderRadius: "8px",
    padding: "16px",
    marginBottom: "32px",
  },
  section: {
    marginBottom: "32px",
  },
  sectionTitle: {
    fontSize: "20px",
    fontWeight: "600",
    marginBottom: "16px",
    color: "#333",
  },
  amazing: {
    color: "#333",
  },
  amenitiesGrid: {
    display: "grid",
    gridTemplateColumns: "repeat(4, 1fr)",
    gap: "16px",
  },
  amenityItem: {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    textAlign: "center",
  },
  amenityIcon: {
    width: "40px",
    height: "40px",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    fontSize: "20px",
    marginBottom: "8px",
    borderRadius: "4px",
    border: "1px solid #ddd",
  },
  amenityName: {
    fontSize: "14px",
    color: "#555",
  },
  noFood: {
    fontSize: "16px",
    fontWeight: "500",
    marginBottom: "12px",
  },
  foodNote: {
    color: "#666",
    marginBottom: "12px",
    fontSize: "14px",
    lineHeight: "1.5",
  },
  detailsGrid: {
    display: "grid",
    gridTemplateColumns: "repeat(2, 1fr)",
    gap: "16px",
    marginTop: "16px",
  },
  detailItem: {
    display: "flex",
    flexDirection: "column",
    gap: "4px",
  },
  detailLabel: {
    fontWeight: "500",
    fontSize: "14px",
  },
  detailValue: {
    fontSize: "16px",
  }
};

export default RoomDetails;