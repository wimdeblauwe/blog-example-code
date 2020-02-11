/// <reference types="Cypress" />

context('Todo tests', () => {
   it('should show a message if there are no todo items', () => {
       cy.request('POST', '/api/integration-test/clear-all-todos');
       cy.visit('/todos');
       cy.get('h1').contains('TODO list');
       cy.get('#empty-todos-message').contains('There are no todo items');
   });

   it('should show all todo items', () => {
       cy.request('POST', '/api/integration-test/prepare-todo-list-items');
       cy.visit('/todos');
       cy.get('h1').contains('TODO list');
       cy.get('#todo-items-list')
           .children()
           .should('have.length', 2)
           .should('contain', 'Add Cypress tests')
           .and('contain', 'Write blog post');
   })
});