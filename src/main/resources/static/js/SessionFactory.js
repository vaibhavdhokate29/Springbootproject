class SessionFactory {
    // Set an item in session storage
    static setItem(key, value) {
        sessionStorage.setItem(key, JSON.stringify(value));
    }

    // Get an item from session storage
    static getItem(key) {
        const item = sessionStorage.getItem(key);
        return item ? JSON.parse(item) : null;
    }

    // Remove an item from session storage
    static removeItem(key) {
        sessionStorage.removeItem(key);
    }

    // Clear all items from session storage
    static clear() {
        sessionStorage.clear();
    }

    // Add an item to a list in session storage
    static addItemToList(key, value) {
        const list = this.getItem(key) || [];
        list.push(value);
        this.setItem(key, list);
    }

    // Remove an item from a list in session storage
    static removeItemFromList(key, value) {
        const list = this.getItem(key) || [];
        const index = list.indexOf(value);
        if (index !== -1) {
            list.splice(index, 1);
            this.setItem(key, list);
        }
    }

    // Get a list from session storage
    static getList(key) {
        return this.getItem(key) || [];
    }
}