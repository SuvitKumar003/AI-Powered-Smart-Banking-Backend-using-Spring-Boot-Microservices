
import torch
import torch.nn as nn
import torch.optim as optim
import json
import random
import numpy as np
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import train_test_split
import joblib

# ==========================================
# 1. CONFIGURATION
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

# Richer Synthetic Data Patterns
DATA_PATTERNS = {
    0: ["starbucks coffee", "mcdonalds burger", "subway sandwich", "dominos pizza", "zomato order", "swiggy food", "restaurant bill", "grocery store", "star market", "whole foods", "kfc chicken", "cafe coffee day"],
    1: ["uber ride", "lyft taxi", "shell petrol", "bp gas station", "indian oil", "irctc train ticket", "indigo flight", "bus fare", "metro recharge", "auto rickshaw"],
    2: ["hescom electricity", "jio fiber internet", "airtel bill", "bsnl phone", "tata sky recharge", "water bill", "house rent payment", "lic insurance", "apartment maintenance"],
    3: ["amazon shopping", "flipkart order", "myntra clothes", "nike shoes", "zara fashion", "h&m", "apple store", "electronics", "decathlon sports", "wallet purchase"],
    4: ["netflix subscription", "spotify premium", "pvr cinemas", "bookmyshow", "steam games", "hotstar vip", "disney plus", "gaming zone", "pub visit"],
    5: ["apollo pharmacy", "medplus", "hospital consultation", "gym membership", "cult fit", "dentist checkup", "blood test", "health insurance"],
    6: ["salary credit", "company payroll", "dividend income", "google adsense payment", "freelance project", "cash deposit", "interest credit", "refund received"]
}

# Hyperparameters
HIDDEN_SIZE = 256
EPOCHS = 50
LEARNING_RATE = 0.005
BATCH_SIZE = 64

# ==========================================
# 2. DATA GENERATION
# ==========================================
def generate_rich_data(num_samples=5000):
    data = []
    labels = []
    for _ in range(num_samples):
        category = random.randint(0, 6)
        pattern = random.choice(DATA_PATTERNS[category])
        
        # Add random noise like dates, transaction IDs, or locations
        noise_prefix = random.choice(["TXN-", "PAY-", "RECH-", "VOUCHER-", ""])
        rand_id = random.randint(1000, 9999) if noise_prefix else ""
        location = random.choice([" Mumbai", " Delhi", " Bangalore", " Online", " Store", ""])
        
        text = f"{noise_prefix}{rand_id} {pattern}{location}".strip().lower()
        data.append(text)
        labels.append(category)
    return data, labels

print("🚀 Generating 5000+ synthetic transactions...")
raw_texts, raw_labels = generate_rich_data(5000)

# ==========================================
# 3. VECTORIZATION (TF-IDF)
# ==========================================
print("📊 Vectorizing text using TF-IDF...")
vectorizer = TfidfVectorizer(max_features=500, stop_words='english')
X_tfidf = vectorizer.fit_transform(raw_texts).toarray()
y_data = np.array(raw_labels)

# Save Vectorizer for Inference
joblib.dump(vectorizer, "tfidf_vectorizer.pkl")

# Train/Test Split
X_train, X_test, y_train, y_test = train_test_split(X_tfidf, y_data, test_size=0.2, random_state=42)

# Prepare Tensors
X_train_t = torch.FloatTensor(X_train)
y_train_t = torch.LongTensor(y_train)
X_test_t = torch.FloatTensor(X_test)
y_test_t = torch.LongTensor(y_test)

# ==========================================
# 4. ADVANCED MODEL DEFINITION
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

input_size = X_tfidf.shape[1]
model = AdvancedClassifier(input_size, HIDDEN_SIZE, len(CATEGORIES))

# Move to GPU if available
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
print(f"💻 Training on: {device}")
model.to(device)

X_train_t, y_train_t = X_train_t.to(device), y_train_t.to(device)
X_test_t, y_test_t = X_test_t.to(device), y_test_t.to(device)

# ==========================================
# 5. TRAINING LOOP
# ==========================================
criterion = nn.CrossEntropyLoss()
optimizer = optim.Adam(model.parameters(), lr=LEARNING_RATE)

print("⏳ Training model...")
for epoch in range(EPOCHS):
    model.train()
    optimizer.zero_grad()
    outputs = model(X_train_t)
    loss = criterion(outputs, y_train_t)
    loss.backward()
    optimizer.step()
    
    if (epoch+1) % 10 == 0:
        model.eval()
        with torch.no_grad():
            test_outputs = model(X_test_t)
            _, predicted = torch.max(test_outputs, 1)
            accuracy = (predicted == y_test_t).sum().item() / len(y_test_t)
            print(f"Epoch [{epoch+1}/{EPOCHS}], Loss: {loss.item():.4f}, Test Accuracy: {accuracy:.2%}")

# ==========================================
# 6. SAVING RESULTS
# ==========================================
torch.save(model.state_dict(), "transaction_classifier_v2.pth")

print("\n✅ BETTER Model saved to 'transaction_classifier_v2.pth'")
print("✅ Vectorizer saved to 'tfidf_vectorizer.pkl'")
print("\nDownload BOTH files and put them in your 'ml-service' folder!")
