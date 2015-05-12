/**
 * Created by artur.shamsiev on 06.05.2015.
 */

(function(sem) {
    var contactObject = {
        createTabs: function(contact) {
            var result = $();
            var tabTitleList = Sem.Utils.uList();

            function createTabTitleItem(prop, title) {
                var item = sem.Utils.lItem();
                var link = sem.Utils.anchor('#' + prop, title);
                item.append(link);
                return item;
            }

            function createTabItem(prop, contact) {
                return sem.Utils.div(prop, contact[prop]);
            }

            function createListTabItem(itemsList, listProp) {
                var tabItem;

                // (itemsList)
                if (arguments.length == 1) {
                    tabItem = sem.Utils.div();
                }
                // (listProp, itemsList)
                if (arguments.length == 2) {
                    tabItem = sem.Utils.div(listProp);
                }

                tabItem.append(sem.Utils.uList(itemsList));
                return tabItem;
            }

            function createEmailsTabItem(listProp, contact) {
                var mails = contact[listProp];

                var listTabItem = createListTabItem(
                    mails.map(function (item, i, arr) {
                        var mailTo = sem.Utils.anchor();
                        mailTo.prop('href', 'mailto:' + item);
                        mailTo.text(item);
                        return mailTo[0].outerHTML;
                    }),
                    listProp
                );
                return listTabItem;
            }

            function createDocumentsTabItem(docProp, contact) {
                var tabItem = sem.Utils.div(docProp);

                tabItem.append(sem.Utils.addFileControl({
                    id: 'contact-documents-control',
                    inputID: 'documentPathInput',
                    btnText: 'Добавить документ',
                    onAdd: function() {alert('this')}
                }));

                var list = contact[docProp].map(function(item, i, arr) {
                    return item['title'];
                });
                if (list) {
                    tabItem.append(createListTabItem(list));
                }
                return tabItem;
            }

            function createImagesTabItem(imageProp, contact) {
                const fromContactPath = '../../data/';

                function sendImage() {
                    $.ajax({
                        type: 'POST',
                        url: 'http://localhost:8080/semantic/restful/contacts/image/' + sem.Utils.id,
                        headers: {
                            Accept : "text/plain; charset=utf-8"
                        },
                        dataType: 'json',
                        data: {
                            imagePath: getImagePathInput()
                        },
                        success: function(data, status, jqXHR) {
                            var answer = data['answer'];

                            if (answer.status == 'exists') {
                                alert('Данное изображение уже существует!')
                            }
                            if (answer.status == 'not-user') {
                                alert('Можно добавлять только файлы из домашнего каталога')
                            }
                            if (answer.status == 'added') {
                                alert('Изображение успешно добавлено');
                                location.reload();
                            }
                        }
                    })
                }

                function getImagePathInput() {
                    return $('#imagePathInput').val();
                }

                var tabItem = sem.Utils.div(imageProp);
                tabItem.append(sem.Utils.addFileControl({
                        id: 'contact-images-control',
                        inputID: 'imagePathInput',
                        btnText: 'Добавить фото',
                        onAdd: sendImage
                    }
                ));

                var imageContainer = sem.Utils.div('contact-image-container');
                imageContainer.css('margin-top', '5px');
                tabItem.append(imageContainer);

                var contactImages = contact[imageProp];
                for (var i = 0; i < contactImages.length; i++) {
                    var img = sem.Utils.sImg(fromContactPath + contactImages[i].path);
                    imageContainer.append(img);
                }

                return tabItem;
            }

            tabTitleList.append(createTabTitleItem('firstName', "Имя"));
            tabTitleList.append(createTabTitleItem('lastName', "Фамилия"));
            tabTitleList.append(createTabTitleItem('emails', "Почта"));
            tabTitleList.append(createTabTitleItem('images', "Фото"));
            tabTitleList.append(createTabTitleItem('documents', "Документы"));

            result = result.add(tabTitleList);
            result = result.add(createTabItem('firstName', contact));
            result = result.add(createTabItem('lastName', contact));
            result = result.add(createEmailsTabItem('emails', contact));
            result = result.add(createImagesTabItem('images', contact));
            result = result.add(createDocumentsTabItem('documents', contact));
            return result;
        }
    };
    sem.Contact = contactObject;
})(Sem);