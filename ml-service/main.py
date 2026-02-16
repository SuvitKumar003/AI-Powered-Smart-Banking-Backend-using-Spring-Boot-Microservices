
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import torch
import torch.nn as nn
import os
import joblib

app = FastAPI(title="Banking AI Service - Pro Edition")

# ==========================================
# 1. ADVANCED MODEL DEFINITION (Must match train_model.py)
# ==========================================
class AdvancedClassifier(nn.Module):
    def __init__(self, input_dim, hidden_dim, output_dim):
        super(AdvancedClassifier, self).__init__()
        self.network = nn.Sequential(
            nn.Linear(input_dim, hidden_dim),
            nn.BatchNorm1d(hidden_dim),
            nn.ReLU(),
            nn.Dropout(0.3),
            nn.Linear(hidden_dim, hidden_dim // 2),
            nn.ReLU(),
            nn.Linear(hidden_dim // 2, output_dim)
        )
    
    def forward(self, x):
        return self.network(x)

# ==========================================
# 2. GLOBALS & LOADING
# ==========================================
CATEGORIES = {
    0: "Food & Dining",
    1: "Transportation",
    2: "Utilities",
    3: "Shopping",
    4: "Entertainment",
    5: "Health & Wellness",
    6: "Income"
}

model = None
vectorizer = None

class TransactionRequest(BaseModel):
    description: str

@app.on_event("startup")
def load_model():
    global model, vectorizer
    
    model_path = "transaction_classifier_v2.pth"
    vectorizer_path = "tfidf_vectorizer.pkl"

    # Check if files exist
    if not os.path.exists(vectorizer_path) or not os.path.exists(model_path):
        print(f"⚠️ Files missing! Run 'train_model.py' first.")
        return

    # Load Vectorizer
    vectorizer = joblib.load(vectorizer_path)
    input_size = len(vectorizer.get_feature_names_out())

    # Load Model
    model = AdvancedClassifier(input_size, 256, len(CATEGORIES))
    model.load_state_dict(torch.load(model_path, map_location=torch.device('cpu')))
    model.eval()
    print("✅ PRO AI Model and Vectorizer loaded successfully!")

# ==========================================
# 3. ENDPOINTS
# ==========================================
@app.get("/")
def health_check():
    return {"status": "AI Service Pro is running", "model_loaded": model is not None}

@app.post("/predict")
def predict_category(request: TransactionRequest):
    if model is None or vectorizer is None:
        raise HTTPException(status_code=503, detail="Model/Vectorizer not loaded.")

    # Preprocess using TF-IDF
    vec = vectorizer.transform([request.description]).toarray()
    input_tensor = torch.FloatTensor(vec)

    # Predict
    with torch.no_grad():
        outputs = model(input_tensor)
        _, predicted = torch.max(outputs, 1)
        category_id = predicted.item()

    return {
        "description": request.description,
        "category_id": category_id,
        "category_name": CATEGORIES[category_id]
    }

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
