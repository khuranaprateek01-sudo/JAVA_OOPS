class Student {
    String uid;
    String name;
    int fineAmount;
    int currentBorrowCount;

    public Student(String uid, String name, int fineAmount, int currentBorrowCount) {
        this.uid =uid;
        this.name =name;
        this.fineAmount =fineAmount;
        this.currentBorrowCount =currentBorrowCount;
    }

    public void validatePolicy() {
        if (fineAmount >0) {
            throw new IllegalStateException("There is a fine"+ fineAmount);
        }
        if (currentBorrowCount >=2) {
            throw new IllegalStateException("Borrow limit exceeded");
        }
    }

    public void incrementBorrow() {
        currentBorrowCount++;
    }
}

class Asset {
    String assetId;
    String assetName;
    boolean available;
    int securityLevel;

    public Asset(String assetId, String assetName, boolean available, int securityLevel) {
        this.assetId =assetId;
        this.assetName =assetName;
        this.available =available;
        this.securityLevel =securityLevel;
    }

    public void validatePolicy(String uid) {
        if (!available) {
            throw new IllegalStateException("Asset not available");
        }
        if (securityLevel ==3 && !uid.startsWith("KRG")) {
            throw new SecurityException(" Only KRG students allowed.");
        }
    }
}

class CheckoutRequest {
    String uid;
    String assetId;
    int hoursRequested;

    public CheckoutRequest(String uid, String assetId, int hoursRequested) {
        this.uid = uid;
        this.assetId = assetId;
        this.hoursRequested = hoursRequested;
        if (hoursRequested < 1 || hoursRequested > 6) {
            throw new IllegalArgumentException("Hours must be 1 to 6");
        }
        
    }
}

class ValidationUtil {
    public static void validateUid(String uid) {
        if (uid == null || uid.length() < 8 || uid.length() > 12 || uid.contains(" ")) {
            throw new IllegalArgumentException("Invalid UID");
        }
    }

    public static void validateAssetId(String assetId) {
        if (assetId == null || !assetId.startsWith("LAB-") ||
                !assetId.substring(4).matches("\\d+")) {
            throw new IllegalArgumentException("Invalid Asset ID");
        }
    }

    public static void validateHours(int hrs) {
        if (hrs < 1 || hrs > 6) {
            throw new IllegalArgumentException("Invalid hours");
        }
    }
}

class AssetStore {
    Asset[] assets;

    public AssetStore(Asset[] assets) {
        this.assets = assets;
    }

    public Asset findAsset(String assetId) {
        for (Asset a : assets) {
            if (a.assetId.equals(assetId)) {
                return a;
            }
        }
        throw new NullPointerException("Asset not found:" + assetId);
    }

    public void markBorrowed(Asset a) {
        if (!a.available) {
            throw new IllegalStateException("Asset not available");
        }
        a.available = false;
    }
}

class CheckoutService {
    Student[] students;
    AssetStore store;

    public CheckoutService(Student[] students, AssetStore store) {
        this.students =students;
        this.store = store;
    }

    private Student findStudent(String uid) {
        for (Student s : students) {
            if (s.uid.equals(uid)) {
                return s;
            }
        }
        throw new NullPointerException("Student not found:" + uid);
    }

    public String checkout(CheckoutRequest req)
            throws IllegalArgumentException, IllegalStateException,
            SecurityException, NullPointerException {

        ValidationUtil.validateUid(req.uid);
        ValidationUtil.validateAssetId(req.assetId);
        ValidationUtil.validateHours(req.hoursRequested);

        Student student = findStudent(req.uid);
        Asset asset = store.findAsset(req.assetId);

        student.validatePolicy();
        asset.validatePolicy(req.uid);

        if (req.hoursRequested == 6) {
            System.out.println("Note: Max duration selected. Return strictly on time.");
        }

        if (asset.assetName.contains("Cable") && req.hoursRequested > 3) {
            req.hoursRequested = 3;
            System.out.println("Policy applied: Cables can be issued max 3 hours. Updated to 3.");
        }

        store.markBorrowed(asset);
        student.incrementBorrow();

        return "TXN-20260221-" + req.assetId + "-" + req.uid;
    }
}

class AuditLogger {
    public static void log(String msg) {
        System.out.println("AUDIT: " + msg);
    }
}

public class Main {
    public static void main(String[] args) {

        Student[] students = {
                new Student("KRG20281", "Prateek", 0, 0),
                new Student("STD10002", "Aman", 200, 1),
                new Student("STD10003", "Riya", 0, 2)
        };

        Asset[] assets = {
                new Asset("LAB-101", "HDMI Cable", true, 1),
                new Asset("LAB-202", "Router", true, 3),
                new Asset("LAB-303", "Mouse", false, 1)
        };

        AssetStore store = new AssetStore(assets);
        CheckoutService service = new CheckoutService(students, store);

        CheckoutRequest[] requests = {
                new CheckoutRequest("KRG20281", "LAB-101", 5),
                new CheckoutRequest("KRG20281", "LAB-999", 2),
                new CheckoutRequest("STD10002", "LAB-202", 2)
        };

        for (CheckoutRequest req : requests) {
            try {
                String receipt = service.checkout(req);
                System.out.println("SUCCESS: " + receipt);
            }
            catch (IllegalArgumentException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
            catch (NullPointerException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
            catch (SecurityException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
            catch (IllegalStateException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
            finally {
                AuditLogger.log("Attempt finished for UID=" 
                        + req.uid + ", asset=" + req.assetId);
                
            }
        }
    }
}