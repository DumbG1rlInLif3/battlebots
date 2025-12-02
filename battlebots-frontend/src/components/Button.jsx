import React from "react";

// Botão simples reutilizável
function Button({ children, onClick, type = "button", disabled = false }) {
  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled}
      style={{
        background: "#2b6cb0",
        color: "#fff",
        border: "none",
        padding: "8px 12px",
        borderRadius: 8,
        cursor: disabled ? "not-allowed" : "pointer",
      }}
    >
      {children}
    </button>
  );
}

export default Button;
