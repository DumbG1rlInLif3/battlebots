// Componente Input reutilizável (label + input)
import React from "react";

function Input({ label, name, value, onChange, type = "text", placeholder = "" }) {
  return (
    <div style={{ marginBottom: 10 }}>
      {label && <label style={{ display: "block", fontSize: 13, color: "#333" }}>{label}</label>}
      <input
        name={name}
        value={value ?? ""}
        onChange={onChange}
        type={type}
        placeholder={placeholder}
        style={{ padding: 8, borderRadius: 6, border: "1px solid #ddd", width: "100%" }}
      />
    </div>
  );
}

export default Input;
