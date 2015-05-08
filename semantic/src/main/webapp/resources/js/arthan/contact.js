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

            function createListTabItem(listProp, Itemslist) {
                var tabItem = sem.Utils.div(listProp);
                tabItem.append(sem.Utils.uList(Itemslist));
                return tabItem;
            }

            function createEmailsTabItem(listProp, contact) {
                var mailItemHtmlList = [];
                var mails = contact[listProp];
                for (var i = 0; i < mails.length; i++) {
                    var mailTo = sem.Utils.anchor();
                    mailTo.prop('href', 'mailto:' + mails[i]);
                    mailTo.text(mails[i]);
                    mailItemHtmlList[i] = mailTo[0].outerHTML;
                }

                return createListTabItem(listProp, mailItemHtmlList);
            }

            function createImagesTabItem(imageProp, contact) {
                const fromContactPath = '../../data/';

                function addImage() {
                    sendImage();
                    //location.reload();
                }

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
                                alert('Можно добавлять только изображения из домашнего каталога')
                            }
                            if (answer.status == 'added') {
                                alert('Изображение успешно добавлено');
                                location.reload();
                            }
                            console.log('Image added!');
                        }
                    })
                }

                function getImagePathInput() {
                    return $('#imagePathInput').val();
                }

                var tabItem = sem.Utils.div(imageProp);
                var imageControls = sem.Utils.div('contact-images-control');
                tabItem.append(imageControls);

                imageControls.append(sem.Utils.btn({
                        id: 'addImageBtn',
                        text: 'Добавить фото',
                        onclick: addImage
                    })
                );

                imageControls.append(sem.Utils.tInput('imagePathInput'));

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
            result = result.add(tabTitleList);

            result = result.add(createTabItem('firstName', contact));
            result = result.add(createTabItem('lastName', contact));
            result = result.add(createEmailsTabItem('emails', contact));
            result = result.add(createImagesTabItem('images', contact));
            return result;
        }
    };
    sem.Contact = contactObject;
})(Sem);