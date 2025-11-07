import csv
import json
import random
from faker import Faker
import os

fake = Faker()

# Crée le dossier datasets si non existant
os.makedirs("datasets/payloads", exist_ok=True)

# =========================
# 1️⃣ Générer categories.csv
# =========================
num_categories = 1000
categories_file = "datasets/categories.csv"

with open(categories_file, mode="w", newline="", encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(["id", "code", "name"])
    for i in range(1, num_categories + 1):
        writer.writerow([i, f"CAT{i:04d}", fake.word().capitalize()])

print(f"[OK] {categories_file} créé avec {num_categories} catégories.")


# =========================
# 2️⃣ Générer items.csv
# =========================
num_items = 10000
items_file = "datasets/items.csv"

with open(items_file, mode="w", newline="", encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(["id", "name", "price", "stock", "category_id"])
    for i in range(1, num_items + 1):
        name = fake.word().capitalize() + " " + fake.word().capitalize()
        price = round(random.uniform(5, 500), 2)
        stock = random.randint(1, 500)
        category_id = random.randint(1, num_categories)
        writer.writerow([i, name, price, stock, category_id])

print(f"[OK] {items_file} créé avec {num_items} items.")


# =========================
# 3️⃣ Générer payloads JSON (format compatible A/B/C)
# =========================

def generate_payload(size_chars):
    return {
        "name": fake.text(max_nb_chars=size_chars),
        "price": round(random.uniform(5, 500), 2),
        "stock": random.randint(1, 500),
        "category": { "id": random.randint(1, num_categories) }
    }

# CREATE payloads
payload_create_1kb = generate_payload(100)
payload_create_5kb = generate_payload(5000)

with open("datasets/payloads/payload_create_1kb.json", "w", encoding="utf-8") as f:
    json.dump(payload_create_1kb, f, indent=4)

with open("datasets/payloads/payload_create_5kb.json", "w", encoding="utf-8") as f:
    json.dump(payload_create_5kb, f, indent=4)

# UPDATE payloads
payload_update_1kb = generate_payload(100)
payload_update_5kb = generate_payload(5000)

with open("datasets/payloads/payload_update_1kb.json", "w", encoding="utf-8") as f:
    json.dump(payload_update_1kb, f, indent=4)

with open("datasets/payloads/payload_update_5kb.json", "w", encoding="utf-8") as f:
    json.dump(payload_update_5kb, f, indent=4)

print("\n✅ Payloads create / update 1kb & 5kb générés avec succès.")
print("🔥 Ton dataset complet est prêt pour benchmark JMeter.")
