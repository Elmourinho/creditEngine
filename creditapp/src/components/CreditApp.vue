<template>
    <div>
        <form @submit.prevent="handleSubmit" class="form">
            <div v-for="field in formFields" :key="field.id" class="form-group">
                <label :for="field.id">{{ field.label }}:</label>
                <input :type="field.type" :id="field.id" v-model="formData[field.key as keyof FormData]"
                    :required="field.required" :min="field.min" :max="field.max">
                <span v-if="formSubmitted && !formData[field.key as keyof FormData]" class="error-message">{{
                    field.errorMessage }}</span>
            </div>
            <button type="submit" class="submit-btn">Submit</button>
        </form>
        <!-- Success message -->
        <div v-if="message" :class="[messageType, 'message']">{{ message }}</div>
    </div>
</template>
  
<script lang="ts">
import axios from 'axios';

interface FormData {
    personalCode: string;
    amount: number | null;
    period: number | null;
}

export default {
    data() {
        return {
            formData: {
                personalCode: '',
                amount: null,
                period: null
            },
            message: '',
            messageType: '',
            formSubmitted: false,
            formFields: [
                { id: 'personalCode', label: 'Personal Code', key: 'personalCode', type: 'text', required: true, errorMessage: 'Personal Code is required' },
                { id: 'amount', label: 'Amount', key: 'amount', type: 'number', required: true, min: 2000, max: 10000, errorMessage: 'Amount must be between 2000 and 10000' },
                { id: 'period', label: 'Period', key: 'period', type: 'number', required: true, min: 12, max: 60, errorMessage: 'Period must be between 12 and 60 months' }
            ]
        };
    },
    methods: {
        handleSubmit() {
            if (!this.isFormValid()) {
                return;
            }
            this.formSubmitted = true;

            axios.post('http://localhost:8080/api/v1/loan/decision', this.formData)
                .then(response => {
                    this.formData = {
                        personalCode: '',
                        amount: null,
                        period: null
                    };
                    this.formSubmitted = false;
                    const isApproved = response.data['decisionType'] === 'POSITIVE';
                    this.setMessage(isApproved ? `Approved amount: ${response.data['approvedAmount']}` : 'Not approved', isApproved ? 'success' : 'error');
                })
                .catch(error => {
                    this.setMessage(error.response.data.message, 'error');
                });
        },
        isFormValid() {
            return this.formFields.every(field => {
                const key = field.key as keyof FormData;
                const value = this.formData[key];
                return value !== null && (typeof value !== 'number' || (value >= (field.min ?? -Infinity) && value <= (field.max ?? Infinity)));
            });
        },
        setMessage(message: string, type: 'success' | 'error') {
            this.message = message;
            this.messageType = type;
            setTimeout(() => {
                this.message = '';
                this.messageType = '';
            }, 3000);
        }
    }
};
</script>
  
<style scoped>
.form {
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: #f9f9f9;
}

.form-group {
    margin-bottom: 15px;
}

label {
    font-weight: bold;
}

input[type="text"],
input[type="number"] {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 3px;
    box-sizing: border-box;
}

.submit-btn {
    display: block;
    width: 100%;
    padding: 10px;
    border: none;
    border-radius: 5px;
    background-color: #4caf50;
    color: white;
    font-size: 16px;
    cursor: pointer;
}

.submit-btn:hover {
    background-color: #45a049;
}

.error-message {
    color: red;
    font-size: 14px;
}

.message {
    margin-top: 20px;
    padding: 10px;
    border-radius: 5px;
}

.message.success {
    background-color: #4caf50;
    color: white;
}

.message.error {
    background-color: #f44336;
    color: white;
}
</style>
  