lines = []
lines.append("-- OPHWC Test Data Script")
lines.append("-- Run as: mysql -u root -p ophwc < test_data.sql")
lines.append("-- Password for ALL users: allow")
lines.append("-- Safe to run multiple times")
lines.append("")
lines.append("USE ophwc;")
lines.append("")

# ---- 1. user_types ----
lines.append("-- 1. USER TYPES")
for t in ["Admin User","HO User","Division User","Client User"]:
    lines.append(f"INSERT INTO user_types (type_name, status) SELECT '{t}','Y' WHERE NOT EXISTS (SELECT 1 FROM user_types WHERE type_name='{t}');")
lines.append("")

# ---- 2. divisions ----
lines.append("-- 2. DIVISIONS")
divs = ["North Division","South Division","East Division","West Division","Central Division",
        "Northeast Division","Northwest Division","Southeast Division","Southwest Division","Metro Division"]
for d in divs:
    lines.append(f"INSERT INTO divisions (division_name, status) SELECT '{d}','Y' WHERE NOT EXISTS (SELECT 1 FROM divisions WHERE division_name='{d}');")
lines.append("")

# ---- 3. nature_of_work ----
lines.append("-- 3. NATURE OF WORK")
natures = ["Road Construction","Bridge Construction","Building Construction","Water Supply",
           "Drainage Works","Electrical Works","Renovation Works","Park Development","Dam Construction","Hospital Construction"]
for n in natures:
    lines.append(f"INSERT INTO nature_of_work (name, status) SELECT '{n}','Y' WHERE NOT EXISTS (SELECT 1 FROM nature_of_work WHERE name='{n}');")
lines.append("")

# ---- 4. project_status ----
lines.append("-- 4. PROJECT STATUS")
for s,c in [("Not Started","N"),("In Progress","N"),("Completed","Y"),("On Hold","N")]:
    lines.append(f"INSERT INTO project_status (status, check_status) SELECT '{s}','{c}' WHERE NOT EXISTS (SELECT 1 FROM project_status WHERE status='{s}');")
lines.append("")

# ---- 5. floors ----
lines.append("-- 5. FLOORS")
for no,name in [(0,"Ground Floor"),(1,"First Floor"),(2,"Second Floor"),(3,"Third Floor"),(4,"Fourth Floor"),(5,"Fifth Floor")]:
    lines.append(f"INSERT INTO floors (floor_no, floor_name, status) SELECT {no},'{name}','Y' WHERE NOT EXISTS (SELECT 1 FROM floors WHERE floor_name='{name}');")
lines.append("")

# ---- 6. floor_work ----
lines.append("-- 6. FLOOR WORK")
for w in ["Flooring","Plastering","Painting","Electrical Wiring","Plumbing","Roofing"]:
    lines.append(f"INSERT INTO floor_work (work_type, status) SELECT '{w}','Y' WHERE NOT EXISTS (SELECT 1 FROM floor_work WHERE work_type='{w}');")
lines.append("")

# ---- 7. user ----
# table: user, columns: division_id, user_type, username, password, first_name, last_name, phone_num, email_id, status
lines.append("-- 7. USERS (password=allow for all)")
users = [
    # (division, type, username, fname, lname, phone, email)
    ("Central Division","Admin User","user-admin","Admin","One","9000000001","user.admin@ophwc.com"),
    ("Central Division","Admin User","user-admin2","Admin","Two","9000000002","user.admin2@ophwc.com"),
    ("Central Division","Admin User","user-admin3","Admin","Three","9000000003","user.admin3@ophwc.com"),
    ("Metro Division","HO User","user-ho","HO","One","9000000011","user.ho@ophwc.com"),
    ("Metro Division","HO User","user-ho2","HO","Two","9000000012","user.ho2@ophwc.com"),
    ("Metro Division","HO User","user-ho3","HO","Three","9000000013","user.ho3@ophwc.com"),
    ("North Division","Division User","user-division","Division","North","9000000021","user.division@ophwc.com"),
    ("South Division","Division User","user-division2","Division","South","9000000022","user.division2@ophwc.com"),
    ("East Division","Division User","user-division3","Division","East","9000000023","user.division3@ophwc.com"),
    ("West Division","Division User","user-division4","Division","West","9000000024","user.division4@ophwc.com"),
    ("Northeast Division","Division User","user-division5","Division","Northeast","9000000025","user.division5@ophwc.com"),
    ("Northwest Division","Division User","user-division6","Division","Northwest","9000000026","user.division6@ophwc.com"),
    ("Southeast Division","Division User","user-division7","Division","Southeast","9000000027","user.division7@ophwc.com"),
    ("Southwest Division","Division User","user-division8","Division","Southwest","9000000028","user.division8@ophwc.com"),
    ("Central Division","Division User","user-division9","Division","Central","9000000029","user.division9@ophwc.com"),
    ("Metro Division","Division User","user-division10","Division","Metro","9000000030","user.division10@ophwc.com"),
    ("North Division","Client User","user-client","Client","One","9000000031","user.client@ophwc.com"),
    ("South Division","Client User","user-client2","Client","Two","9000000032","user.client2@ophwc.com"),
    ("East Division","Client User","user-client3","Client","Three","9000000033","user.client3@ophwc.com"),
    ("West Division","Client User","user-client4","Client","Four","9000000034","user.client4@ophwc.com"),
    ("Central Division","Client User","user-client5","Client","Five","9000000035","user.client5@ophwc.com"),
]
for div,utype,uname,fname,lname,phone,email in users:
    div_sub  = f"(SELECT id FROM divisions WHERE division_name='{div}')"
    type_sub = f"(SELECT id FROM user_types WHERE type_name='{utype}')"
    lines.append(
        f"INSERT INTO user (division_id, user_type, username, password, first_name, last_name, phone_num, email_id, status) "
        f"SELECT {div_sub},{type_sub},'{uname}','allow','{fname}','{lname}','{phone}','{email}','Y' "
        f"WHERE NOT EXISTS (SELECT 1 FROM user WHERE username='{uname}');"
    )
lines.append("")

# ---- 8. projects ----
# table: projects, columns: project_division, created_user, natur_of_project(typo!), client_name,
#   project_name, project_defination, project_location, latitude, langitude, status, is_handovered
lines.append("-- 8. PROJECTS")
ADMIN = "(SELECT id FROM user WHERE username='user-admin')"
def div(d): return f"(SELECT id FROM divisions WHERE division_name='{d}')"
def nat(n): return f"(SELECT id FROM nature_of_work WHERE name='{n}')"

projects = [
    # (division, nature, client, name, defination, location, lat, lng, handovered)
    ("North Division","Road Construction","Govt of India","NH-44 Road Widening Phase 1","Widening of national highway","Jammu","32.7266","74.8570","N"),
    ("North Division","Bridge Construction","NHAI","Ravi River Bridge","Bridge over Ravi river","Pathankot","32.2745","75.6522","N"),
    ("North Division","Drainage Works","Punjab Govt","Amritsar Drainage Upgrade","City wide drainage upgrade","Amritsar","31.6340","74.8723","N"),
    ("North Division","Hospital Construction","PGIMER","Chandigarh Hospital Wing C","New hospital wing","Chandigarh","30.7333","76.7794","N"),
    ("North Division","Building Construction","Punjab PWD","Patiala Admin Complex","New admin complex","Patiala","30.3398","76.3869","N"),
    ("South Division","Building Construction","State Govt","District Collectorate Building","New collectorate building","Chennai","13.0827","80.2707","N"),
    ("South Division","Water Supply","CMWSSB","Chennai Water Pipeline Phase 2","Underground pipeline laying","Chennai","13.0500","80.2100","N"),
    ("South Division","Road Construction","TNRDC","Coimbatore Bypass Road","Bypass road construction","Coimbatore","11.0168","76.9558","N"),
    ("South Division","Park Development","GHMC","Hyderabad Botanical Garden","Botanical garden development","Hyderabad","17.4500","78.4700","N"),
    ("South Division","Electrical Works","TANGEDCO","Chennai Smart Grid Phase 1","Smart grid installation","Chennai","13.0100","80.2500","N"),
    ("East Division","Drainage Works","Kolkata MC","Kolkata Storm Drain Upgrade","Storm water drain upgrade","Kolkata","22.5726","88.3639","N"),
    ("East Division","Road Construction","WBPWD","Howrah Bypass Road","Construction of bypass road","Howrah","22.5958","88.2636","N"),
    ("East Division","Building Construction","Odisha Govt","Bhubaneswar Convention Centre","State convention centre","Bhubaneswar","20.2961","85.8245","N"),
    ("East Division","Water Supply","PHED Bihar","Patna Water Supply Upgrade","Water supply network upgrade","Patna","25.5941","85.1376","N"),
    ("East Division","Bridge Construction","NHAI","Mahanadi Bridge","Bridge over Mahanadi river","Cuttack","20.4625","85.8830","N"),
    ("West Division","Hospital Construction","State Health Dept","District Hospital Block B","New hospital block","Ahmedabad","23.0225","72.5714","N"),
    ("West Division","Dam Construction","Irrigation Dept","Sabarmati Dam Restoration","Restoration of dam structure","Gandhinagar","23.2156","72.6369","N"),
    ("West Division","Road Construction","Goa PWD","Panaji Coastal Road","Coastal road widening","Panaji","15.4909","73.8278","N"),
    ("West Division","Renovation Works","Maharashtra PWD","Mumbai Heritage Building Restoration","Restoration of heritage structures","Mumbai","18.9388","72.8354","N"),
    ("West Division","Park Development","BMC","Sanjay Gandhi Park Extension","Park area extension","Mumbai","19.2147","72.8793","N"),
    ("Central Division","Building Construction","Central PWD","Bhopal Secretariat Annex","New secretariat annex building","Bhopal","23.2599","77.4126","N"),
    ("Central Division","Electrical Works","MPEB","Rural Electrification Phase 3","Electrification of rural areas","Indore","22.7196","75.8577","N"),
    ("Central Division","Road Construction","MPRDC","Raipur Ring Road","Ring road development","Raipur","21.2514","81.6296","N"),
    ("Central Division","Bridge Construction","MPRDC","Narmada Valley Bridge","Bridge over Narmada valley","Jabalpur","23.1815","79.9864","N"),
    ("Central Division","Water Supply","MPJAL","Bhopal Water Grid","City water grid expansion","Bhopal","23.2700","77.4000","N"),
    ("Northeast Division","Road Construction","MORTH","Guwahati Ring Road","Ring road around Guwahati","Guwahati","26.1445","91.7362","N"),
    ("Northeast Division","Bridge Construction","NHAI","Brahmaputra Cable Bridge","Cable bridge over Brahmaputra","Dibrugarh","27.4728","94.9120","N"),
    ("Northeast Division","Building Construction","Meghalaya Govt","Shillong Admin Block","New government admin block","Shillong","25.5788","91.8933","N"),
    ("Northeast Division","Drainage Works","Tripura Govt","Agartala City Drainage","City drainage overhaul","Agartala","23.8315","91.2868","N"),
    ("Northeast Division","Hospital Construction","Assam Health","Guwahati Medical Block","New medical college block","Guwahati","26.1700","91.7500","N"),
    ("Northwest Division","Water Supply","Rajasthan Jal Board","Jaisalmer Water Supply Project","Desert water supply scheme","Jaisalmer","26.9157","70.9083","N"),
    ("Northwest Division","Road Construction","NHAI","Jodhpur-Jaisalmer Highway","Four-lane highway construction","Jodhpur","26.2389","73.0243","N"),
    ("Northwest Division","Building Construction","HP Govt","Shimla Govt Housing Complex","Government housing complex","Shimla","31.1048","77.1734","N"),
    ("Northwest Division","Hospital Construction","JK Health","Srinagar Hospital Expansion","Hospital expansion project","Srinagar","34.0837","74.7973","N"),
    ("Northwest Division","Dam Construction","BBMB","Bhakra Dam Maintenance","Maintenance of Bhakra dam","Bilaspur","31.4167","76.7500","N"),
    ("Southeast Division","Building Construction","AP Govt","Visakhapatnam Court Complex","New district court complex","Visakhapatnam","17.6868","83.2185","N"),
    ("Southeast Division","Park Development","VMRDA","Rushikonda Beach Park","Beach front park development","Visakhapatnam","17.7726","83.3748","N"),
    ("Southeast Division","Water Supply","APEWIDC","Tirupati Water Grid","Water grid construction","Tirupati","13.6288","79.4192","N"),
    ("Southeast Division","Road Construction","NHAI","Kochi Expressway","Coastal expressway project","Kochi","9.9312","76.2673","N"),
    ("Southeast Division","Drainage Works","HMWSSB","Hyderabad Drainage Phase 2","Drainage network upgrade","Hyderabad","17.3850","78.4867","N"),
    ("Southwest Division","Road Construction","Karnataka PWD","Mysore-Bangalore Expressway","Six-lane expressway widening","Mysore","12.2958","76.6394","N"),
    ("Southwest Division","Dam Construction","KPCL","Cauvery Dam Extension","Extension of dam capacity","Mandya","12.5220","76.8950","N"),
    ("Southwest Division","Electrical Works","BESCOM","Bangalore Smart Grid","Smart grid upgrade","Bangalore","12.9716","77.5946","N"),
    ("Southwest Division","Park Development","BBMP","Cubbon Park Restoration","Heritage park restoration","Bangalore","12.9763","77.5929","N"),
    ("Southwest Division","Hospital Construction","Kerala Health","Thiruvananthapuram Medical Block","New medical college block","Thiruvananthapuram","8.5241","76.9366","N"),
    ("Metro Division","Building Construction","DDA","Delhi Metro Office Complex","New metro admin office complex","New Delhi","28.6139","77.2090","N"),
    ("Metro Division","Renovation Works","CPWD","Parliament Annexe Renovation","Renovation of Parliament Annexe","New Delhi","28.6179","77.2078","N"),
    ("Metro Division","Road Construction","NHAI","Delhi Peripheral Expressway","Peripheral expressway project","Gurugram","28.4595","77.0266","N"),
    ("Metro Division","Building Construction","NDMC","Connaught Place Redevelopment","CP area redevelopment","New Delhi","28.6315","77.2167","N"),
    ("Metro Division","Drainage Works","DJB","Delhi Sewage Treatment Plant","New sewage treatment plant","New Delhi","28.5800","77.1900","N"),
    # Handed over
    ("North Division","Road Construction","Punjab PWD","Ludhiana Road Phase 1","Completed road project","Ludhiana","30.9010","75.8573","Y"),
    ("South Division","Building Construction","TN Govt","Madurai Court Building","Completed court building","Madurai","9.9252","78.1198","Y"),
    ("Metro Division","Renovation Works","MCD","Old Delhi Market Renovation","Completed renovation","Old Delhi","28.6562","77.2410","Y"),
]

for d,n,client,pname,pdef,ploc,lat,lng,handed in projects:
    lines.append(
        f"INSERT INTO projects (project_division, created_user, natur_of_project, client_name, project_name, project_defination, project_location, latitude, langitude, status, is_handovered) "
        f"SELECT {div(d)},{ADMIN},{nat(n)},'{client}','{pname}','{pdef}','{ploc}','{lat}','{lng}','Y','{handed}' "
        f"WHERE NOT EXISTS (SELECT 1 FROM projects WHERE project_name='{pname}');"
    )
lines.append("")

# ---- 9. project_resources ----
# table: project_resources, columns: project_id, user_id, nature_of_project, status
lines.append("-- 9. PROJECT RESOURCES")
def proj(p): return f"(SELECT id FROM projects WHERE project_name='{p}')"
def usr(u):  return f"(SELECT id FROM user WHERE username='{u}')"

resources = [
    ("NH-44 Road Widening Phase 1","user-division","Road Construction"),
    ("Ravi River Bridge","user-division","Bridge Construction"),
    ("Amritsar Drainage Upgrade","user-division","Drainage Works"),
    ("Patiala Admin Complex","user-division","Building Construction"),
    ("District Collectorate Building","user-division2","Building Construction"),
    ("Chennai Water Pipeline Phase 2","user-division2","Water Supply"),
    ("Coimbatore Bypass Road","user-division2","Road Construction"),
    ("Kolkata Storm Drain Upgrade","user-division3","Drainage Works"),
    ("Howrah Bypass Road","user-division3","Road Construction"),
    ("Bhubaneswar Convention Centre","user-division3","Building Construction"),
    ("District Hospital Block B","user-division4","Hospital Construction"),
    ("Sabarmati Dam Restoration","user-division4","Dam Construction"),
    ("Guwahati Ring Road","user-division5","Road Construction"),
    ("Brahmaputra Cable Bridge","user-division5","Bridge Construction"),
    ("Jaisalmer Water Supply Project","user-division6","Water Supply"),
    ("Srinagar Hospital Expansion","user-division6","Hospital Construction"),
    ("Visakhapatnam Court Complex","user-division7","Building Construction"),
    ("Kochi Expressway","user-division7","Road Construction"),
    ("Mysore-Bangalore Expressway","user-division8","Road Construction"),
    ("Bangalore Smart Grid","user-division8","Electrical Works"),
    ("Bhopal Secretariat Annex","user-division9","Building Construction"),
    ("Narmada Valley Bridge","user-division9","Bridge Construction"),
    ("Delhi Metro Office Complex","user-division10","Building Construction"),
    ("Parliament Annexe Renovation","user-division10","Renovation Works"),
    ("Delhi Peripheral Expressway","user-division10","Road Construction"),
]
for pname,uname,nname in resources:
    lines.append(
        f"INSERT INTO project_resources (project_id, user_id, nature_of_project, status) "
        f"SELECT {proj(pname)},{usr(uname)},{nat(nname)},'Y' "
        f"WHERE NOT EXISTS (SELECT 1 FROM project_resources WHERE project_id={proj(pname)} AND user_id={usr(uname)});"
    )
lines.append("")

# ---- 10. project_amount ----
# table: project_amount, columns: project_id, amount, remarks(NOT comments!), created_date, from_date, to_date
lines.append("-- 10. PROJECT AMOUNTS")
amounts = [
    ("NH-44 Road Widening Phase 1","5000000","Initial budget","2026-01-01","2026-06-30"),
    ("Ravi River Bridge","12000000","Bridge budget","2026-02-01","2026-08-31"),
    ("District Collectorate Building","8000000","Building budget","2026-01-15","2026-07-15"),
    ("Chennai Water Pipeline Phase 2","3500000","Pipeline budget","2026-03-01","2026-09-30"),
    ("Kolkata Storm Drain Upgrade","4500000","Drainage budget","2026-04-01","2026-10-31"),
    ("District Hospital Block B","15000000","Hospital budget","2026-02-01","2026-10-31"),
    ("Bhopal Secretariat Annex","6000000","Secretariat budget","2026-03-01","2026-09-30"),
    ("Guwahati Ring Road","18000000","Ring road budget","2026-01-01","2026-12-31"),
    ("Jaisalmer Water Supply Project","7500000","Water supply budget","2026-03-01","2026-10-31"),
    ("Delhi Metro Office Complex","25000000","Metro office budget","2026-01-01","2026-12-31"),
    ("Bangalore Smart Grid","9000000","Smart grid budget","2026-05-01","2026-10-31"),
    ("Mysore-Bangalore Expressway","22000000","Expressway budget","2026-01-01","2026-12-31"),
    ("Brahmaputra Cable Bridge","35000000","Cable bridge budget","2026-02-01","2026-12-31"),
    ("Visakhapatnam Court Complex","11000000","Court complex budget","2026-04-01","2026-12-31"),
    ("Parliament Annexe Renovation","8500000","Renovation budget","2026-06-01","2026-12-31"),
]
for pname,amt,rem,fd,td in amounts:
    lines.append(
        f"INSERT INTO project_amount (project_id, amount, remarks, created_date, from_date, to_date) "
        f"SELECT {proj(pname)},'{amt}','{rem}',NOW(),'{fd}','{td}' "
        f"WHERE NOT EXISTS (SELECT 1 FROM project_amount WHERE project_id={proj(pname)});"
    )
lines.append("")

# ---- Verification ----
lines.append("-- VERIFICATION")
lines.append("SELECT 'user_types'       AS tbl, COUNT(*) AS cnt FROM user_types")
lines.append("UNION ALL SELECT 'divisions',          COUNT(*) FROM divisions")
lines.append("UNION ALL SELECT 'nature_of_work',     COUNT(*) FROM nature_of_work")
lines.append("UNION ALL SELECT 'project_status',     COUNT(*) FROM project_status")
lines.append("UNION ALL SELECT 'floors',             COUNT(*) FROM floors")
lines.append("UNION ALL SELECT 'floor_work',         COUNT(*) FROM floor_work")
lines.append("UNION ALL SELECT 'user',               COUNT(*) FROM user")
lines.append("UNION ALL SELECT 'projects',           COUNT(*) FROM projects")
lines.append("UNION ALL SELECT 'project_resources',  COUNT(*) FROM project_resources")
lines.append("UNION ALL SELECT 'project_amount',     COUNT(*) FROM project_amount;")
lines.append("")
lines.append("SELECT u.username, ut.type_name AS role, d.division_name AS division, u.status")
lines.append("FROM user u")
lines.append("JOIN user_types ut ON u.user_type = ut.id")
lines.append("JOIN divisions d   ON u.division_id = d.id")
lines.append("ORDER BY ut.type_name, u.username;")

with open("C:/sandbox/ophwc-2/ophwc/test_data.sql", "w", encoding="utf-8") as f:
    f.write("\n".join(lines))

print(f"Done. Total lines: {len(lines)}")

