// groupCache.js
class GroupCache {
    constructor() {
        this.cache = this.loadFromStorage();
        this.maxAge = 15 * 60 * 1000; // 15 minutes
    }
    
    loadFromStorage() {
        try {
            const stored = sessionStorage.getItem('groupCache');
            if (stored) {
                const data = JSON.parse(stored);
                return new Map(Object.entries(data));
            }
        } catch (error) {
            console.error('Failed to load cache:', error);
        }
        return new Map();
    }
    
    saveToStorage() {
        try {
            const obj = Object.fromEntries(this.cache);
            sessionStorage.setItem('groupCache', JSON.stringify(obj));
        } catch (error) {
            console.error('Failed to save cache:', error);
        }
    }
    
    has(groupId) {
        return this.cache.has(groupId);
    }
    
    get(groupId) {
        const entry = this.cache.get(groupId);
        
        // Check if stale
        if (entry && this.isStale(entry)) {
            console.log(`Cache for group ${groupId} is stale`);
            this.delete(groupId);
            return null;
        }
        
        return entry;
    }
    
    set(groupId, data) {
        // Add timestamp
        const entry = {
            ...data,
            _cachedAt: Date.now()
        };
        
        this.cache.set(groupId, entry);
        this.saveToStorage();
    }
    
    isStale(entry) {
        if (!entry._cachedAt) return true;
        const age = Date.now() - entry._cachedAt;
        return age > this.maxAge;
    }
    
    delete(groupId) {
        this.cache.delete(groupId);
        this.saveToStorage();
    }
    
    clear() {
        this.cache.clear();
        sessionStorage.removeItem('groupCache');
    }
}

export const groupCache = new GroupCache();